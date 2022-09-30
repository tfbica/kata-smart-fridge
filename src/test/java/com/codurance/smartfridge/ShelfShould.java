package com.codurance.smartfridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ShelfShould {

    private Shelf shelf;
    @Mock private Item item;

    @BeforeEach
    void setUp() {
        shelf = new Shelf();
    }

    @Test
    void add() {
        shelf.add(item);

        assertThat(shelf.getItems().iterator().next()).isEqualTo(item);
    }

    @Test
    void remove() {

        given(item.getName()).willReturn("Milk");

        shelf.add(item);
        shelf.remove("Milk");

        assertThat(shelf.getItems().size()).isEqualTo(0);
    }

    @Test
    void degrade_sealed_item() {

        shelf.add(item);

        shelf.degradeItems();

        then(item).should().degradeItem();
    }
}