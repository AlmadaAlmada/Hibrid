INSERT INTO usuario (nome, email, senha, endereco) VALUES
('Vaqueiro Dono da Vaca', 'vaqueirodonodavaca@gmail.com', '123456', 'Avenida Rio Grande do Sul, 22, Centro, Dois Vizinhos, Parana');

INSERT INTO animal (nome, codigo, tipo, sexo, especie, raca, peso, data_nascimento, data_vacinacao, producao_leite_diaria, producao_leite_mensal, producao_media_carne, procriacoes, ultima_procriacao, produzindo_leite, destino, usuario_id) VALUES
('Mimosa', '001', 'VACA', 'FEMEA', 'Gado-bovino-domestico', 'Holstein-Frisia', 396, '2017-12-19', '2026-01-20', 25, 725, 0, 23, '2026-03-20', TRUE, 'Producao', 1),
('Estrela', '002', 'VACA', 'FEMEA', 'Gado-bovino-domestico', 'Jersey', 412, '2018-04-10', '2026-01-15', 22, 638, 0, 18, '2026-02-11', TRUE, 'Producao', 1),
('Florzinha', '003', 'VACA', 'FEMEA', 'Gado-bovino-domestico', 'Girolando', 520, '2019-06-02', '2026-01-18', 28, 812, 0, 12, '2026-04-05', TRUE, 'Producao', 1),
('Pintada', '004', 'VACA', 'FEMEA', 'Gado-bovino-domestico', 'Holstein-Frisia', 478, '2016-09-30', '2026-01-22', 30, 870, 0, 27, '2026-01-30', TRUE, 'Producao', 1),
('Malhada', '005', 'VACA', 'FEMEA', 'Gado-bovino-domestico', 'Gir Leiteiro', 445, '2018-11-12', '2026-01-20', 19, 551, 0, 14, '2026-03-02', TRUE, 'Producao', 1),
('Bela', '006', 'VACA', 'FEMEA', 'Gado-bovino-domestico', 'Girolando', 505, '2020-02-25', '2026-01-19', 24, 696, 0, 8, '2026-04-18', TRUE, 'Producao', 1),
('Lua', '007', 'VACA', 'FEMEA', 'Gado-bovino-domestico', 'Jersey', 398, '2019-08-08', '2026-01-21', 21, 609, 0, 10, '2026-02-28', TRUE, 'Producao', 1),
('Princesa', '008', 'VACA', 'FEMEA', 'Gado-bovino-domestico', 'Holstein-Frisia', 462, '2017-05-14', '2026-01-17', 27, 783, 0, 21, '2026-03-15', TRUE, 'Producao', 1),
('Boneca', '009', 'VACA', 'FEMEA', 'Gado-bovino-domestico', 'Gir Leiteiro', 430, '2021-03-19', '2026-01-16', 0, 0, 0, 2, '2026-05-01', FALSE, 'Recria', 1),
('Donzela', '010', 'VACA', 'FEMEA', 'Gado-bovino-domestico', 'Girolando', 488, '2018-01-07', '2026-01-23', 26, 754, 0, 16, '2026-02-09', TRUE, 'Producao', 1),
('Trovao', '011', 'BOI', 'MACHO', 'Gado-bovino-domestico', 'Nelore', 780, '2022-07-21', '2026-01-12', 0, 0, 320, 0, NULL, FALSE, 'Corte', 1),
('Valente', '012', 'BOI', 'MACHO', 'Gado-bovino-domestico', 'Angus', 712, '2022-10-03', '2026-01-12', 0, 0, 298, 0, NULL, FALSE, 'Corte', 1),
('Pingo', '013', 'BEZERRO', 'MACHO', 'Gado-bovino-domestico', 'Girolando x Angus', 92, '2025-11-15', '2026-01-25', 0, 0, 0, 0, NULL, FALSE, 'Recria', 1),
('Faisca', '014', 'BEZERRO', 'FEMEA', 'Gado-bovino-domestico', 'Holstein x Gir', 84, '2025-12-02', '2026-01-25', 0, 0, 0, 0, NULL, FALSE, 'Recria', 1);

INSERT INTO semen (codigo, raca, especie, peso, grau_sangue, preco, fornecedor) VALUES
('SM-A12', 'Angus', 'Gado-bovino-domestico', 850, '1/2', 180.00, 'Central Genetica Sul'),
('SM-N07', 'Nelore', 'Gado-bovino-domestico', 900, '3/4', 150.00, 'Central Genetica Sul'),
('SM-H21', 'Holstein-Frisia', 'Gado-bovino-domestico', 820, 'PO', 240.00, 'Genetica Premium'),
('SM-G15', 'Gir Leiteiro', 'Gado-bovino-domestico', 760, '1/2', 165.00, 'Fazenda Boa Vista'),
('SM-J09', 'Jersey', 'Gado-bovino-domestico', 690, 'PO', 210.00, 'Genetica Premium'),
('SM-B03', 'Brahman', 'Gado-bovino-domestico', 880, '3/4', 175.00, 'Central Genetica Sul');

INSERT INTO predicao (vaca_id, vaca_nome, semen_id, semen_codigo, score_genetico, producao_leite_estimada, ganho_peso_estimado, indice_fertilidade, tipo_cruzamento, resultado_previsto, recomendacao, criado_em) VALUES
(1, 'Mimosa', 1, 'SM-A12', 85, 24, 1.5, 78, 'Holstein-Frisia x Angus', 'Alto Potencial', 'Cruzamento indicado para gerar femeas leiteiras e machos com boa aptidao para corte.', '2026-05-20 09:30:00'),
(2, 'Estrela', 2, 'SM-N07', 72, 21, 1.4, 70, 'Jersey x Nelore', 'Aptidao Corte', 'Boa rusticidade e ganho de peso para os machos; femeas com producao moderada.', '2026-05-21 14:10:00'),
(3, 'Florzinha', 3, 'SM-H21', 88, 28, 1.2, 81, 'Girolando x Holstein-Frisia', 'Alto Potencial', 'Excelente projecao leiteira para as femeas; recomendado para producao.', '2026-05-22 11:05:00');
