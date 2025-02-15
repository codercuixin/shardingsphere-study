/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.example.readwritesplitting.spring.boot.starter.jpa.repository;

import org.apache.shardingsphere.example.readwritesplitting.spring.boot.starter.jpa.entity.OrderItem;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class OrderItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS t_order_item (\n" +
                "    order_item_id BIGSERIAL PRIMARY KEY,\n" +
                "    order_id BIGINT NOT NULL,\n" +
                "    user_id INT NOT NULL,\n" +
                "    phone VARCHAR(50),\n" +
                "    status VARCHAR(50)\n" +
                ");";
        entityManager.createNativeQuery(sql).executeUpdate();

    }

    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS t_order_item";
        entityManager.createNativeQuery(sql).executeUpdate();

    }

    public void truncateTable() {
        String sql = "TRUNCATE TABLE t_order_item";
        entityManager.createNativeQuery(sql).executeUpdate();

    }

    public Long insert(final OrderItem orderItem) {
        entityManager.persist(orderItem);
        return orderItem.getOrderItemId();
    }

    public void delete(final Long id) {
        Query query = entityManager.createQuery("DELETE FROM OrderItem i WHERE i.orderId = ?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    public List<OrderItem> selectAll() {
        return (List<OrderItem>) entityManager.createQuery("SELECT o from OrderItem o").getResultList();
    }
}
