package com.chaski.Backend_chaski.repository;

import com.chaski.Backend_chaski.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailIgnoreCase(String email);
    Optional<Usuario> findByTelefono(String telefono);
    Optional<Usuario> findByFirebaseUid(String firebaseUid);

    boolean existsByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByTelefono(String telefono);
    boolean existsByFirebaseUid(String firebaseUid);
}

