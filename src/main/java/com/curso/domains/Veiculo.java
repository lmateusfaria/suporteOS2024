package com.curso.domains;

import com.curso.domains.dtos.ProdutoDTO;
import com.curso.domains.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
    private Long idProduto;

    @NotBlank @NotNull
    @Column(unique = true)
    private String codigoBarra;

    @NotBlank @NotNull
    private String descricao;

    @NotNull
    @Digits(integer = 15, fraction = 3)
    private BigDecimal saldoEstoque;

    @NotNull
    @Digits(integer = 15, fraction = 3)
    private BigDecimal valorUnitario;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    private BigDecimal valorEstoque;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro = LocalDate.now();

    @ManyToOne
    @JoinColumn(name="idgrupoproduto")
    private GrupoProduto grupoProduto;

    @Enumerated(EnumType.ORDINAL)
    @JoinColumn(name="status")
    private Status status;

    public Produto() {
        this.saldoEstoque = BigDecimal.ZERO;
        this.valorUnitario = BigDecimal.ZERO;
        this.valorEstoque = BigDecimal.ZERO;
        this.status = Status.ATIVO;
    }

    public Produto(Long idProduto, String codigoBarra, String descricao, BigDecimal saldoEstoque, BigDecimal valorUnitario,
                   LocalDate dataCadastro, GrupoProduto grupoProduto, Status status) {
        this.idProduto = idProduto;
        this.codigoBarra = codigoBarra;
        this.descricao = descricao;
        this.saldoEstoque = saldoEstoque;
        this.valorUnitario = valorUnitario;
        this.dataCadastro = dataCadastro;
        this.grupoProduto = grupoProduto;
        this.status = status;
        this.valorEstoque = saldoEstoque.multiply(valorUnitario)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public Produto(ProdutoDTO dto) {
        this.idProduto = dto.getIdProduto();
        this.codigoBarra = dto.getCodigoBarra();
        this.descricao = dto.getDescricao();
        this.valorUnitario = dto.getValorUnitario();
        this.saldoEstoque = dto.getSaldoEstoque();
        this.dataCadastro = dto.getDataCadastro();
        this.status = Status.toEnum(dto.getStatus());;
        //atribuição do grupoProduto
        this.grupoProduto = new GrupoProduto();
        this.grupoProduto.setId(dto.getGrupoProduto());
        //arrondonda para baixo e para cima,
        // se estiver exatamento no meio arredonda para cima
        this.valorEstoque = dto.getSaldoEstoque().multiply(valorUnitario)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getCodigoBarra() { return codigoBarra; }

    public void setCodigoBarra(String codigoBarra) { this.codigoBarra = codigoBarra; }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getSaldoEstoque() {
        return saldoEstoque;
    }

    public void setSaldoEstoque(BigDecimal saldoEstoque) {
        this.saldoEstoque = saldoEstoque;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public GrupoProduto getGrupoProduto() {
        return grupoProduto;
    }

    public void setGrupoProduto(GrupoProduto grupoProduto) {
        this.grupoProduto = grupoProduto;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public @NotNull @Digits(integer = 15, fraction = 2) BigDecimal getValorEstoque() {
        return valorEstoque;
    }

    public void setValorEstoque(@NotNull @Digits(integer = 15, fraction = 2) BigDecimal valorEstoque) {
        this.valorEstoque = valorEstoque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return idProduto == produto.idProduto && Objects.equals(descricao, produto.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, descricao);
    }
}
