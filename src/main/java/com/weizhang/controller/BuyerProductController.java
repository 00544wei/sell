package com.weizhang.controller;

import com.weizhang.entity.ProductCategory;
import com.weizhang.entity.ProductInfo;
import com.weizhang.service.ProductCategoryService;
import com.weizhang.service.ProductService;
import com.weizhang.vo.ProductInfoVO;
import com.weizhang.vo.ProductVO;
import com.weizhang.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService categoryService;

    @RequestMapping("/list")
    public ResultVO list(){
        //查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findpUpAll();
        //查询类目 一次性查询
        List<Integer> categotyTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categotyTypeList);
        //数据拼装
        for (ProductCategory productCategory: productCategoryList) {
            ProductVO productVO = new ProductVO();
            
        }
        //
        ResultVO result = new ResultVO();
        result.setCode(1);
        result.setMsg("不好意思，没有数据");
        ProductVO productVO = new ProductVO();
        ProductInfoVO productInfoVO = new ProductInfoVO();
        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));
        result.setData(productVO);
        return result;
    }


}
