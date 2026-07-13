package cn.source.system.domain;

import java.util.List;

/**
 * @Description: 定义首页数据类型，返回封装好的数据
 * @author: 詹sir
 * @date: 2022年05月03日 10:39
 */
public class MallIndex {

    /**广告*/
    private List<MallAdvert> advertList;

    /**导航*/
    private List<MallNavigate> navigateList;

    /**商品*/
    private List<MallGoods> goodsList;

    public List<MallAdvert> getAdvertList() {
        return advertList;
    }

    public void setAdvertList(List<MallAdvert> advertList) {
        this.advertList = advertList;
    }

    public List<MallNavigate> getNavigateList() {
        return navigateList;
    }

    public void setNavigateList(List<MallNavigate> navigateList) {
        this.navigateList = navigateList;
    }

    public List<MallGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<MallGoods> goodsList) {
        this.goodsList = goodsList;
    }
}
