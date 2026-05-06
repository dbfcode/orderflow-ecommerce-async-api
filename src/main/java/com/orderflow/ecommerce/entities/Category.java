package com.orderflow.ecommerce.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "tb_category")
@Schema(description = "Categoria de produtos")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador gerado pelo banco", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "O nome da categoria é obrigatório")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ ]+$", message = "O nome deve conter apenas letras")
    @Schema(description = "Nome único da categoria (apenas letras e espaços)", example = "Eletrônicos")
    private String name;
}
