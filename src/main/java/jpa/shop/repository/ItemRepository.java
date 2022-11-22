package jpa.shop.repository;

import jpa.shop.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Item item){
        if(item.getId()==null){
            //식별자 값이 없으면 새로운 엔티티로서 persist()로 영속화
            em.persist(item);
        }else{
            //식별자 값이 있으면 준영속상태의 엔티티로서 merge()를 통해 영속화
            // (영속화 후 변경감지, 커밋때 자동 수정)
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("SELECT i FROM Item i", Item.class)
                .getResultList();
    }
}
