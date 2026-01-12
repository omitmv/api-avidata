package com.avidata.api.domain.enumerador;

public enum SexoEnum {
  MACHO("M", "Macho"),
  FEMEA("F", "Fêmea"),
  INDEFINIDO("I", "Indefinido");

  private final String codigo;
  private final String descricao;

  SexoEnum(String codigo, String descricao) {
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
