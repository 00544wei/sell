package com.weizhang.controller;

import com.weizhang.converter.OrderForm2OrderDTOConverter;
import com.weizhang.dto.OrderDTO;
import com.weizhang.enu.ResultEnum;
import com.weizhang.exception.SellException;
import com.weizhang.form.OrderForm;
import com.weizhang.service.impl.OrderMasterServiceImpl;
import com.weizhang.util.ResultVOUtils;
import com.weizhang.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterServiceImpl orderMasterService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】 创建订单失败，参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.ORDER_CREATE_FALT_PARAMETER_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 创建订单失败，购物车为空，orderForm={}", orderForm);
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderMasterService.create(orderDTO);

        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", createResult.getOrderId());
        return ResultVOUtils.success(map);
    }
    //查看列表

    //查看详情

    //支付订单

    //取消订单
}
