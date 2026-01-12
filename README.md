# api-avidata

Estrutura do Banco de Dados

CREATE TABLE especies (
    id INT PRIMARY KEY AUTO_INCREMENT,
    classe VARCHAR(50) NOT NULL,
    familia VARCHAR(100) NOT NULL,
    genero VARCHAR(100) NOT NULL,
    especie VARCHAR(150) NOT NULL,
    nome_popular VARCHAR(150) NOT NULL,
    nome_cientifico VARCHAR(200) GENERATED ALWAYS AS (CONCAT(genero, ' ', especie)) STORED,
    ativo BOOLEAN DEFAULT TRUE
);

INSERT INTO especies (classe, familia, genero, especie, nome_popular) VALUES
('Aves', 'Thraupidae', 'Sporophila', 'caerulescens', 'Coleiro'),
('Aves', 'Thraupidae', 'Sporophila', 'angolensis', 'Curió'),
('Aves', 'Thraupidae', 'Sporophila', 'maximiliani', 'Bicudo'),
('Aves', 'Thraupidae', 'Sporophila', 'nigricollis', 'Papa-capim-de-costas'),
('Aves', 'Thraupidae', 'Sporophila', 'leucoptera', 'Papa-capim-de-asa-branca'),
('Aves', 'Thraupidae', 'Sporophila', 'lineola', 'Bigodinho'),
('Aves', 'Thraupidae', 'Sporophila', 'bouvreuil', 'Caboclinho'),
('Aves', 'Thraupidae', 'Saltator', 'similis', 'Trinca-ferro'),
('Aves', 'Thraupidae', 'Saltator', 'coerulescens', 'Azulão'),
('Aves', 'Thraupidae', 'Sicalis', 'flaveola', 'Canário-da-terra'),
('Aves', 'Emberizidae', 'Zonotrichia', 'capensis', 'Tico-tico'),
('Aves', 'Icteridae', 'Gnorimopsar', 'chopi', 'Chopim'),
('Aves', 'Icteridae', 'Icterus', 'jamacaii', 'Corrupião'),
('Aves', 'Cardinalidae', 'Cyanoloxia', 'brissonii', 'Azulão-verdadeiro'),
('Aves', 'Cardinalidae', 'Paroaria', 'dominicana', 'Cardeal-do-nordeste'),
('Aves', 'Cardinalidae', 'Paroaria', 'coronata', 'Cardeal-do-sul'),
('Aves', 'Passeridae', 'Passer', 'domesticus', 'Pardal'),
('Aves', 'Estrildidae', 'Lonchura', 'punctulata', 'Capuchinho'),
('Aves', 'Estrildidae', 'Lonchura', 'striata', 'Manon'),
('Aves', 'Estrildidae', 'Estrilda', 'astrild', 'Bico-de-lacre');

CREATE TABLE plantel (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT NULL,
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE aves (
    id INT PRIMARY KEY AUTO_INCREMENT,
    -- Identificação básica
    identificador VARCHAR(50) UNIQUE NOT NULL,
    nome VARCHAR(100) NULL,
    -- Dados biológicos
    especie_id INT NOT NULL,
    sexo ENUM('M', 'F', 'Indefinido') DEFAULT 'Indefinido',
    data_nascimento DATE NULL,
    cor VARCHAR(100) NULL,
    -- Identificação oficial
    numero_anilha VARCHAR(50) UNIQUE NULL,
    anilha_tipo ENUM('SISPASS', 'FOB', 'Outros') NULL,
    ano_anilha INT NULL,
    -- Genealogia
    pai_id INT NULL,
    mae_id INT NULL,
    -- Relação com o plantel
    plantel_id INT NOT NULL,
    -- Status no plantel
    status ENUM('Ativo', 'Vendido', 'Morto', 'Emprestado', 'Perdido') DEFAULT 'Ativo',
    data_entrada DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_saida DATE NULL,
    -- Observações
    observacoes TEXT NULL,
    -- Relacionamentos
    FOREIGN KEY (especie_id) REFERENCES especies(id),
    FOREIGN KEY (pai_id) REFERENCES aves(id),
    FOREIGN KEY (mae_id) REFERENCES aves(id),
    FOREIGN KEY (plantel_id) REFERENCES plantel(id)
);
