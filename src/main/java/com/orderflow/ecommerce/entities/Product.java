package com.orderflow.ecommerce.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_product")
@Schema(description = "Produto do catálogo; em POST/PUT pode referenciar categoria apenas com {\"id\": <id>}")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador gerado pelo banco", accessMode = Schema.AccessMode.READ_ONLY, example = "10")
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ ]+$", message = "O nome deve conter apenas letras")
    @Column(nullable = false)
    @Schema(description = "Nome do produto (apenas letras e espaços)", example = "Teclado mecânico")
    private String name;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Descrição longa do produto")
    private String description;

    @NotNull(message = "O preço é obrigatório")
    @Column(nullable = false)
    @Schema(description = "Preço unitário", example = "199.90")
    private BigDecimal price;

    @Schema(description = "Quantidade em estoque (opcional)")
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Schema(description = "Categoria associada; no corpo JSON pode ser só {\"id\": 1}")
    private Category category;
}