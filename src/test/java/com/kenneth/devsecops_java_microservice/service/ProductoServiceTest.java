package com.kenneth.devsecops_java_microservice.service;

import com.kenneth.devsecops_java_microservice.model.Producto;
import com.kenneth.devsecops_java_microservice.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto(
                "Laptop",
                "Laptop para desarrollo",
                8500.0,
                10
        );

        producto.setId(1L);
    }

    @Test
    void debeListarProductos() {
        when(productoRepository.findAll())
                .thenReturn(List.of(producto));

        List<Producto> resultado = productoService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Laptop", resultado.get(0).getNombre());

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void debeGuardarProducto() {
        when(productoRepository.save(producto))
                .thenReturn(producto);

        Producto resultado = productoService.guardar(producto);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombre());
        assertEquals(10, resultado.getStock());

        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void debeBuscarProductoPorId() {
        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        Producto resultado = productoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void debeActualizarProducto() {
        Producto actualizado = new Producto(
                "Laptop Pro",
                "Laptop actualizada",
                9000.0,
                8
        );

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        when(productoRepository.save(any(Producto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Producto resultado =
                productoService.actualizar(1L, actualizado);

        assertEquals("Laptop Pro", resultado.getNombre());
        assertEquals("Laptop actualizada", resultado.getDescripcion());
        assertEquals(9000.0, resultado.getPrecio());
        assertEquals(8, resultado.getStock());

        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void debeEliminarProducto() {
        productoService.eliminar(1L);

        verify(productoRepository, times(1))
                .deleteById(1L);
    }

    @Test
    void debeLanzarErrorSiProductoNoExiste() {
        when(productoRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productoService.buscarPorId(99L)
        );

        assertEquals(
                "Producto no encontrado",
                exception.getMessage()
        );
    }
}