package com.avidata.api.domain.enumerador;

public enum StatusEnum {
  ATIVO("A", "Ativo"),
  VENDIDO("V", "Vendido"),
  MORTO("M", "Morto"),
  EMPRESTADO("E", "Emprestado"),
  PERDIDO("P", "Perdido");

  private final String codigo;
  private final String descricao;

  StatusEnum(String codigo, String descricao) {
    this.codigo = codigo;
    this.descricao = descricao;
  }

  public String getCodigo() {
    return codigo;
  }

  public String getDescricao() {
    return descricao;
  }
}
