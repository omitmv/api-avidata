package com.avidata.api.domain.enumerador;

public enum TipoAnilhaEnum {
  SISPASS("S", "SISPASS"),
  FOB("F", "FOB"),
  OUTRA("O", "OUTRA");

  private final String codigo;
  private final String descricao;

  TipoAnilhaEnum(String codigo, String descricao) {
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
