package jpa.shop.service;

import jpa.shop.domain.item.Item;
import jpa.shop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {
    @Autowired ItemRepository repository;

    public  void saveItem(Item item){
        repository.save(item);;
    }

    public List<Item> findItems(){
        return repository.findAll();
    }

    public Item findOne(Long id){
        return repository.findOne(id);
    }
}
