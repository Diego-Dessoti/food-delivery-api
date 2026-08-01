package com.diego.fooddeliveryapi.service;

import com.diego.fooddeliveryapi.dto.request.AddressRequestDTO;
import com.diego.fooddeliveryapi.dto.request.CreateOrderRequestDTO;
import com.diego.fooddeliveryapi.dto.request.OrderItemsRequestDTO;
import com.diego.fooddeliveryapi.dto.request.UpdateOrderRequestDTO;
import com.diego.fooddeliveryapi.dto.response.AddressResponseDTO;
import com.diego.fooddeliveryapi.dto.response.OrderItemResponseDTO;
import com.diego.fooddeliveryapi.dto.response.OrderResponseDTO;
import com.diego.fooddeliveryapi.entity.Address;
import com.diego.fooddeliveryapi.entity.Order;
import com.diego.fooddeliveryapi.entity.OrderItem;
import com.diego.fooddeliveryapi.entity.User;
import com.diego.fooddeliveryapi.enums.OrderStatus;
import com.diego.fooddeliveryapi.exception.InvalidOrderStatusTransitionException;
import com.diego.fooddeliveryapi.exception.OrderNotfoundException;
import com.diego.fooddeliveryapi.exception.UserNotFoundException;
import com.diego.fooddeliveryapi.repository.OrderRepository;
import com.diego.fooddeliveryapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public OrderResponseDTO create(CreateOrderRequestDTO request, String authenticatedEmail) {

        User user = userRepository.findByEmail(authenticatedEmail).orElseThrow(UserNotFoundException::new);

        Order order = new Order();
        order.setCustomerName(request.customerName().trim());
        order.setAddress(toAddress(request.deliveryAddress()));
        order.setOrderStatus(OrderStatus.RECEBIDO);
        order.setCreatedBy(user);

        BigDecimal totalValue = BigDecimal.ZERO;

        for (OrderItemsRequestDTO itemsRequest : request.items()) {
            OrderItem item = new OrderItem();

            item.setProductName(itemsRequest.productName());
            item.setQuantity(itemsRequest.quantity());
            item.setUnitValue(itemsRequest.unitValue());

            order.addItem(item);

            BigDecimal subTotal = itemsRequest.unitValue()
                    .multiply(BigDecimal.valueOf(itemsRequest.quantity()));

            totalValue = totalValue.add(subTotal);
        }

        order.setTotalValue(totalValue);
        Order savedOrder = orderRepository.save(order);

        return toResponse(savedOrder);
    }

    @Transactional
    public OrderResponseDTO updateStatus(Long id, UpdateOrderRequestDTO request) {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotfoundException::new);

        OrderStatus currentStatus = order.getOrderStatus();
        OrderStatus newStatus = request.status();


        if (!isValidUpdate(currentStatus, newStatus)) {
            throw new InvalidOrderStatusTransitionException(
                    currentStatus,
                    newStatus
            );

        }
            order.setOrderStatus(newStatus);
        return toResponse(order);
    }

    public boolean isValidUpdate(OrderStatus currentStatus, OrderStatus newStatus) {
        return switch (currentStatus) {
            case RECEBIDO -> newStatus == OrderStatus.EM_PREPARO || newStatus == OrderStatus.CANCELADO;

            case EM_PREPARO -> newStatus == OrderStatus.SAIU_PARA_ENTREGA || newStatus == OrderStatus.CANCELADO;

            case SAIU_PARA_ENTREGA -> newStatus == OrderStatus.ENTREGUE || newStatus == OrderStatus.CANCELADO;

            case ENTREGUE, CANCELADO -> false;
        };
    }

    public List<OrderResponseDTO> findAll() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotfoundException::new);

        return toResponse(order);
    }

    public OrderResponseDTO toResponse(Order order) {
        Address address = order.getAddress();

        AddressResponseDTO addressResponseDTO = new AddressResponseDTO(
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getComplement()
        );

        List<OrderItemResponseDTO> itemResponseDTOList = order.getItems().stream()
                .map(item -> {
                    BigDecimal subTotal = item.getUnitValue().multiply(BigDecimal.valueOf(item.getQuantity()));

                    return new OrderItemResponseDTO(
                            item.getId(),
                            item.getProductName(),
                            item.getQuantity(),
                            item.getUnitValue(),
                            subTotal
                    );
                }).toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getCustomerName(),
                addressResponseDTO,
                order.getOrderStatus(),
                order.getTotalValue(),
                order.getCreatedAt(),
                itemResponseDTOList

        );
    }

    public Address toAddress(AddressRequestDTO request) {
        Address address = new Address();

        address.setStreet(request.street().trim());
        address.setNumber(request.number().trim());
        address.setNeighborhood(request.neighborhood().trim());
        address.setCity(request.city().trim());
        address.setState(request.state().trim());
        address.setZipCode(request.zipCode().trim());
        address.setComplement(request.complement() == null ? null : request.complement().trim());
        return address;
    }
}
