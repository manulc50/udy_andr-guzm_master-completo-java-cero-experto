package org.aguzman.apiservlet.webapp.session.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carro {
    private final List<ItemCarro> items;

    public Carro() {
        items = new ArrayList<>();
    }

    public List<ItemCarro> getItems() {
        return items;
    }

    // Si la línea de producto ya existe en el carro, se actualiza su cantidad, y, en caso contrario, se añade
    public void addItem(ItemCarro item) {
        // En este caso, para realizar las comparaciones por id y por nombre del producto con el método "equals", sobrescribimos la implementación de dicho método en la clase "ItemCarro"
        items.stream().filter(i -> i.equals(item))
                .findFirst() // Otra opción es usar el método "findAny"
                .ifPresentOrElse(i -> i.setCantidad(i.getCantidad() + 1), () -> items.add(item));
    }

    public void removeProductos(List<String> productoIds) {
        if (productoIds != null) {
            productoIds.forEach(this::removeProducto); // Versión simplificada de la expresión "productoId -> removeProducto(productoId)"
        }
    }

    public void removeProducto(String productoId) {
        Optional<ItemCarro> producto = findProducto(productoId);
        producto.ifPresent(items::remove); // Versión simplificada de la expresión "itemCarro -> items.remove(itemCarro)"
    }

    public void updateCantidad(String productoId, int cantidad) {
        Optional<ItemCarro> producto = findProducto(productoId);
        producto.ifPresent(itemCarro -> itemCarro.setCantidad(cantidad));
    }

    public int getTotal() {
        return items.stream().mapToInt(ItemCarro::getImporte) // Versión simplificada de la expresión "i -> i.getImporte()"
                .sum();
    }

    private Optional<ItemCarro> findProducto(String productoId) {
        return  items.stream()
                .filter(itemCarro -> productoId.equals(Long.toString(itemCarro.getProducto().getId())))
                .findAny();
    }
}
