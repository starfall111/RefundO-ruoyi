package com.refund.root.mapper;

import com.refund.root.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 产品Mapper接口（APP端专用）
 *
 * @author refund
 */
@Mapper
public interface RfProductsMapper {

    /**
     * 根据hash值查询产品ID
     *
     * @param hash 产品hash值
     * @return 产品ID，不存在则返回null
     */
    Long findIdByHash(@Param("hash") String hash);

    /**
     * 根据产品ID查询产品信息
     *
     * @param productId 产品ID
     * @return 产品信息，不存在则返回null
     */
    Product selectById(@Param("productId") Long productId);

    /**
     * 插入产品信息
     *
     * @param product 产品信息
     * @return 影响行数
     */
    int insert(Product product);
}
