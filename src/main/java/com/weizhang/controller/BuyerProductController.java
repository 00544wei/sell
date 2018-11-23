package com.weizhang.controller;

import com.weizhang.entity.ProductCategory;
import com.weizhang.entity.ProductInfo;
import com.weizhang.service.ProductCategoryService;
import com.weizhang.service.ProductService;
import com.weizhang.vo.ProductInfoVO;
import com.weizhang.vo.ProductVO;
import com.weizhang.vo.ResultVO;
import org.springframework.beans.BeanUtils;
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
        List<ProductVO> data = new ArrayList<ProductVO>();
        List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();
        for (ProductCategory productCategory: productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            for (ProductInfo productInfo : productInfoList) {
                ProductInfoVO productInfoVO = new ProductInfoVO();
                BeanUtils.copyProperties(productInfo, productInfoVO);
                productInfoVOList.add(productInfoVO);
            }
            productVO.setProductInfoVOList(productInfoVOList);
            data.add(productVO);
        }
        //
        ResultVO result = new ResultVO();
        result.setCode(1);
        result.setMsg("不好意思，没有数据");
        result.setData(data);
        return result;
    }


}
