package org.example.repositories;

import org.example.entities.PedidoAutoPecas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PedidoAutoPecasRepository extends JpaRepository<PedidoAutoPecas, Long> {

    /**
     * Atualiza o ID de um pedido
     * Usado para renumerar os IDs após exclusão
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE pedido_auto_pecas SET PED_ID = :novoId WHERE PED_ID = :idAntigo", nativeQuery = true)
    void updatePedidoId(@Param("idAntigo") Long idAntigo, @Param("novoId") Long novoId);

    /**
     * Reseta o contador de auto-incremento do banco de dados MySQL
     */
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE pedido_auto_pecas AUTO_INCREMENT = :proximoId", nativeQuery = true)
    void resetAutoIncrementMySQL(@Param("proximoId") Long proximoId);
    
    /**
     * Desabilita temporariamente as verificações de chave estrangeira (MySQL)
     */
    @Modifying
    @Transactional
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void disableForeignKeyChecks();
    
    /**
     * Habilita as verificações de chave estrangeira (MySQL)
     */
    @Modifying
    @Transactional
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void enableForeignKeyChecks();
}