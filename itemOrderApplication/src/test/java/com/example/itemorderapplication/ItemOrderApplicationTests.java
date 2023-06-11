package com.example.itemorderapplication;

import com.example.itemorderapplication.domain.Order;
import com.example.itemorderapplication.domain.OrderItem;
import com.example.itemorderapplication.service.ItemService;
import com.example.itemorderapplication.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@Execution(ExecutionMode.CONCURRENT)
class ItemOrderApplicationTests {

	ItemService itemService = new ItemService();
	OrderService orderService = new OrderService();
	Long targetItemId = 1L;

	@DisplayName("병렬 테스트 - SoldOutException 테스트1")
	@Test
	void 병렬_테스트1() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));


	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트2")
	@Test
	void 병렬_테스트2() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트3")
	@Test
	void 병렬_테스트3() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트4")
	@Test
	void 병렬_테스트4() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트5")
	@Test
	void 병렬_테스트5() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트6")
	@Test
	void 병렬_테스트6() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트7")
	@Test
	void 병렬_테스트7() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트8")
	@Test
	void 병렬_테스트8() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트9")
	@Test
	void 병렬_테스트9() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트10")
	@Test
	void 병렬_테스트10() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트11")
	@Test
	void 병렬_테스트11() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트12")
	@Test
	void 병렬_테스트12() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트13")
	@Test
	void 병렬_테스트13() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트14")
	@Test
	void 병렬_테스트14() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트15_5")
	@Test
	void 병렬_테스트16() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 5));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트16_4")
	@Test
	void 병렬_테스트17() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 4));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트17")
	@Test
	void 병렬_테스트18() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트18")
	@Test
	void 병렬_테스트19() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트19")
	@Test
	void 병렬_테스트20() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트20")
	@Test
	void 병렬_테스트21() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트21")
	@Test
	void 병렬_테스트22() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트22")
	@Test
	void 병렬_테스트23() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트23")
	@Test
	void 병렬_테스트24() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트24")
	@Test
	void 병렬_테스트25() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트25")
	@Test
	void 병렬_테스트26() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}

	@DisplayName("병렬 테스트 - SoldOutException 테스트26")
	@Test
	void 병렬_테스트27() throws InterruptedException {
		Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
		orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 3));
		orderService.order(new Order(orderItemsWithItemId));
	}


}
