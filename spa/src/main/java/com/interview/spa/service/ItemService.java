package com.interview.spa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interview.exception.ResourceNotFoundException;
import com.interview.spa.dto.ItemDTO;
import com.interview.spa.entity.Item;
import com.interview.spa.repository.ItemRepository;

@Service
@Transactional
public class ItemService {
	
	private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
	}
	
    @Transactional(readOnly = true)
    public List<ItemDTO> getAllEntries() {
        return itemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ItemDTO createItem(ItemDTO itemDTO) {
        validateItemDTO(itemDTO);
        Item item = convertToEntity(itemDTO);
        return convertToDTO(itemRepository.save(item));
    }

    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id " + id));
        itemRepository.delete(item);
    }

    private ItemDTO convertToDTO(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot convert null Item to DTO");
        }
        
        return ItemDTO.builder()
                .id(item.getId())
                .number(item.getNumber())
                .type(item.getType())
                .text(item.getText())
                .build();
    }	

    private Item convertToEntity(ItemDTO itemDTO) {
        if (itemDTO == null) {
            throw new IllegalArgumentException("Cannot convert null DTO to Item");
        }
        return Item.builder()
                .number(itemDTO.getNumber())
                .type(itemDTO.getType())
                .text(itemDTO.getText())
                .build();
    }

    private void validateItemDTO(ItemDTO itemDTO) {
        if (itemDTO.getNumber() == null) {
            throw new IllegalArgumentException("Item number must not be null");
        }
        if (itemDTO.getType() == null) {
            throw new IllegalArgumentException("Item type must not be null");
        }
    }

}
