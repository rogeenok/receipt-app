package com.netcracker.checkapp.server.persistance.customs;

import com.netcracker.checkapp.server.model.ShopStats;

import java.util.List;

public interface CheckRepositoryCustom {

    /*
    this method gets statistics for user with grouping by shops: min, avg, max, sum(total) spends
    and it should be implemented customly
    */
    List<ShopStats> getShopStats();
}
