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

  public static SexoEnum fromCodigo(String codigo) {
    for (SexoEnum sexo : SexoEnum.values()) {
      if (sexo.getCodigo().equalsIgnoreCase(codigo)) {
        return sexo;
      }
    }
    throw new IllegalArgumentException("Código de sexo inválido: " + codigo);
  }

  public static SexoEnum fromDescricao(String descricao) {
    for (SexoEnum sexo : SexoEnum.values()) {
      if (sexo.getDescricao().equalsIgnoreCase(descricao)) {
        return sexo;
      }
    }
    throw new IllegalArgumentException("Descrição de sexo inválida: " + descricao);
  }
}
