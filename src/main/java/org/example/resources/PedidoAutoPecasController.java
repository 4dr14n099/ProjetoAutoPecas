package org.example.resources;

import org.example.entities.PedidoAutoPecas;
import org.example.repositories.PedidoAutoPecasRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class PedidoAutoPecasController {

    private PedidoAutoPecasRepository pedidoAutoPecasRepository;

    @GetMapping
    public List<PedidoAutoPecas> listarTodos() {
        return pedidoAutoPecasRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoAutoPecas> buscarPorId(@PathVariable Long id) {
        return pedidoAutoPecasRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidoAutoPecas> criar(@RequestBody PedidoAutoPecas pedido) {
        try {
            PedidoAutoPecas salvo = pedidoAutoPecasRepository.save(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<PedidoAutoPecas> atualizar(@PathVariable Long id, @RequestBody PedidoAutoPecas pedido) {
//        return pedidoAutoPecasRepository.findById(id)
//                .map(pedidoExistente -> {
//                    pedido.pedId(id);
//                    PedidoAutoPecas atualizado = pedidoAutoPecasRepository.save(pedido);
//                    return ResponseEntity.ok(atualizado);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return pedidoAutoPecasRepository.findById(id)
                .map(pedido -> {
                    pedidoAutoPecasRepository.delete(pedido);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
