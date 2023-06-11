package com.example.itemorderapplication;

import com.example.itemorderapplication.common.Const;
import com.example.itemorderapplication.common.enumeration.ErrorType;
import com.example.itemorderapplication.common.exception.SoldOutException;
import com.example.itemorderapplication.common.util.ConsoleUtil;
import com.example.itemorderapplication.domain.Order;
import com.example.itemorderapplication.domain.OrderItem;
import com.example.itemorderapplication.service.ItemService;
import com.example.itemorderapplication.service.OrderService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
//@SpringBootApplication
public class ItemOrderApplication {

	private static OrderService orderService = new OrderService();
	private static ItemService itemService = new ItemService();


	public static void main(String[] args) {
		// 스프링 빈 사용 X
		//SpringApplication.run(ItemOrderApplication.class, args);
		startOrderApplication();
	}

	private static void startOrderApplication() {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		Order order = new Order(orderItemsWithItemId);

		String inputValue;
		while (!ConsoleUtil.isFinishConsole(inputValue = ConsoleUtil.inputStrPrefix(Const.ORDER_APP_START_MENT))) {
			boolean isCompleteStatusItemId = false;
			if (Const.ITEM_ORDER.equals(inputValue.toLowerCase())) {
				// 상품 주문 입력시 상품 리스트 콘솔에 출력
				itemService.showConsoleOrganizedAllItems();

				// 상품 번호 입력
				Long itemId = itemService.getCorrectItemIdWithInput();
				if (Objects.isNull(itemId)) {
					// 주문 도중 Q || q 입력 시 모든 주문 클리어 후 애플리케이션 종료
					log.info("고객님이 {} 중 주문 애플리케이션을 종료하셨습니다.", Order.StopOrderFieldType.ITEM_ID.getAction());
					inputValue = Const.QUIT;
					break;
				} else if (itemId == 0) {
					// 0 -> 주문 완료 & 결제 - 스페이스바 + 엔터
					if (!orderService.hasOrderItem(orderItemsWithItemId)) {
						continue;
					}

					isCompleteStatusItemId = true;
				}

				// 수량 입력
				Integer itemQuantity = itemService.getCorrectItemQuantityWithInput(itemId);
				if (Objects.isNull(itemQuantity)) {
					// 주문 도중 Q || q 입력 시 모든 주문 클리어 후 애플리케이션 종료
					log.info("고객님이 {} 중 주문 애플리케이션을 종료하셨습니다.", Order.StopOrderFieldType.ITEM_ID.getAction());
					inputValue = Const.QUIT;
					break;
				} else if (itemQuantity == 0) {
					if (!isCompleteStatusItemId) {
						log.warn(Const.NOT_INPUT_CORRECTLY_WITH_ITEM_ID_MENT);
						continue;
					}

					if (!orderService.hasOrderItem(orderItemsWithItemId)) {
						continue;
					}

					inputValue = Const.ORDER_COMPLETE;
					break;
				}

				order.addOrderItemQuantity(itemId, itemQuantity);
				itemService.monitoringExceedItemQuantity(itemId, orderItemsWithItemId.get(itemId).getQuantity());

			} else if (Const.CURRENT_ORDER_BASKET.equals(inputValue.toLowerCase())) {
				orderService.showCurrentOrderItemBasket(order);
			} else if (Const.MINUS_ORDER_ITEM_QUANTITY.equals(inputValue.toLowerCase())) {
				orderService.minusOrderItemQuantity(order);
			}
		}

		if (Const.QUIT.equals(inputValue.toLowerCase())) {
			System.out.println(orderService.quitOrder());
		} else if (Const.ORDER_COMPLETE.equals(inputValue.toLowerCase())) {
			// 스프링을 사용하지 않아서 RestControllerAdvice 대신 try catch 로직 추가
			try {
				orderService.order(order);
			} catch (SoldOutException e) {
				log.error(e.getErrorResult().getMessage());
				System.out.println(ErrorType.SOLD_OUT_ITEM.getMsg());
			} finally {
				startOrderApplication();
			}
		}
	}

}
