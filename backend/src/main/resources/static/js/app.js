const view = document.getElementById("view");
const estado = { rota: "inicio", usuario: null };
const coresGrafico = ["#7d12b8", "#b06fe0", "#d6b3f0", "#e9d6fb"];

function formatarData(iso) {
    if (!iso) {
        return "-";
    }
    const partes = iso.substring(0, 10).split("-");
    return partes[2] + "/" + partes[1] + "/" + partes[0];
}

function rotular(tipo) {
    if (tipo === "VACA") return "Vaca";
    if (tipo === "BOI") return "Boi";
    if (tipo === "BEZERRO") return "Bezerro";
    return tipo;
}

function destacarMenu() {
    document.querySelectorAll(".menu-item").forEach(item => {
        item.classList.toggle("ativo", item.dataset.rota === estado.rota);
    });
}

function navegar(rota) {
    estado.rota = rota;
    destacarMenu();
    render();
}

async function carregarUsuario() {
    try {
        estado.usuario = await Api.usuarioAtual();
        document.getElementById("perfilNome").textContent = estado.usuario.nome;
        document.getElementById("perfilEmail").textContent = estado.usuario.email;
        document.querySelector(".avatar").textContent = estado.usuario.nome.charAt(0);
    } catch (e) {
        document.getElementById("perfilNome").textContent = "Sem usuario";
    }
}

function render() {
    if (estado.rota === "inicio") return renderInicio();
    if (estado.rota === "animais") return renderAnimais();
    if (estado.rota === "semen") return renderSemen();
    if (estado.rota === "analise") return renderAnalise();
    if (estado.rota === "usuario") return renderUsuario();
}

async function renderInicio() {
    view.innerHTML = '<h1 class="titulo">Dashboard</h1><div class="painel"><p>Carregando...</p></div>';
    const d = await Api.dashboard();
    const cards = [
        { rotulo: "Quantidade Total de Vacas", valor: d.totalVacas },
        { rotulo: "Quantidade de Vacas Produzindo", valor: d.vacasProduzindo },
        { rotulo: "Leite Produzido Mensal (L)", valor: d.leiteMensal.toLocaleString("pt-BR") },
        { rotulo: "Leite Produzido Diario (L)", valor: d.leiteDiario.toLocaleString("pt-BR") },
        { rotulo: "Quantidade Total de Bois", valor: d.totalBois },
        { rotulo: "Quantidade Total de Bezerros", valor: d.totalBezerros }
    ];
    const html = cards.map(c =>
        '<div class="card"><div class="rotulo">' + c.rotulo + '</div><div class="valor">' + c.valor + '</div></div>'
    ).join("");
    view.innerHTML = '<h1 class="titulo">Dashboard</h1><div class="painel"><div class="cards">' + html + '</div></div>';
}

async function renderAnimais() {
    view.innerHTML = '<h1 class="titulo">Animais</h1><div class="painel"><p>Carregando...</p></div>';
    const animais = await Api.listarAnimais();
    const linhas = animais.map(a =>
        '<tr>' +
        '<td><button class="acao-icone" data-editar="' + a.id + '">Editar</button></td>' +
        '<td><button class="acao-icone" data-excluir="' + a.id + '">Excluir</button></td>' +
        '<td>' + a.id + '</td>' +
        '<td>' + (a.nome || "-") + '</td>' +
        '<td>' + rotular(a.tipo) + '</td>' +
        '<td>' + (a.raca || "-") + '</td>' +
        '<td>' + formatarData(a.dataVacinacao) + '</td>' +
        '<td>' + formatarData(a.dataNascimento) + '</td>' +
        '</tr>'
    ).join("");
    view.innerHTML =
        '<h1 class="titulo">Animais</h1>' +
        '<div class="painel"><table><thead><tr>' +
        '<th></th><th></th><th>Id</th><th>Nome</th><th>Tipo</th><th>Raca</th>' +
        '<th>Data da Vacinacao</th><th>Data de Nascimento</th>' +
        '</tr></thead><tbody>' + linhas + '</tbody></table>' +
        '<div class="botoes"><button class="btn btn-primario" id="novoAnimal">+ Cadastrar</button></div></div>';

    document.getElementById("novoAnimal").addEventListener("click", () => abrirFormAnimal(null));
    view.querySelectorAll("[data-editar]").forEach(b =>
        b.addEventListener("click", () => abrirFormAnimal(Number(b.dataset.editar))));
    view.querySelectorAll("[data-excluir]").forEach(b =>
        b.addEventListener("click", () => excluirAnimal(Number(b.dataset.excluir))));
}

async function excluirAnimal(id) {
    if (!confirm("Deseja excluir este animal?")) {
        return;
    }
    await Api.excluirAnimal(id);
    renderAnimais();
}

async function abrirFormAnimal(id) {
    let animal = {
        tipo: "VACA", sexo: "FEMEA", especie: "Gado-bovino-domestico",
        produzindoLeite: false, procriacoes: 0
    };
    if (id) {
        animal = await Api.buscarAnimal(id);
    }

    const campo = (label, attr, valor, tipoInput) =>
        '<div class="campo"><label>' + label + '</label>' +
        '<input data-campo="' + attr + '" type="' + (tipoInput || "text") + '" value="' + (valor != null ? valor : "") + '"></div>';

    const selectSimNao = (valor) =>
        '<div class="campo"><label>Produzindo Leite</label><select data-campo="produzindoLeite">' +
        '<option value="true"' + (valor ? " selected" : "") + '>Sim</option>' +
        '<option value="false"' + (!valor ? " selected" : "") + '>Nao</option></select></div>';

    const selectTipo =
        '<div class="campo"><label>Tipo</label><select data-campo="tipo">' +
        ["VACA", "BOI", "BEZERRO"].map(t =>
            '<option value="' + t + '"' + (animal.tipo === t ? " selected" : "") + '>' + rotular(t) + '</option>').join("") +
        '</select></div>';

    const selectSexo =
        '<div class="campo"><label>Sexo</label><select data-campo="sexo">' +
        ["FEMEA", "MACHO"].map(s =>
            '<option value="' + s + '"' + (animal.sexo === s ? " selected" : "") + '>' + s + '</option>').join("") +
        '</select></div>';

    const abaGeral =
        '<div class="form-grid" data-aba="geral">' +
        campo("Codigo", "codigo", animal.codigo) +
        campo("Nome", "nome", animal.nome) +
        selectTipo + selectSexo +
        campo("Especie", "especie", animal.especie) +
        campo("Raca", "raca", animal.raca) +
        campo("Peso (Kg)", "peso", animal.peso, "number") +
        campo("Nascimento", "dataNascimento", animal.dataNascimento ? animal.dataNascimento.substring(0, 10) : "", "date") +
        campo("Data da Vacinacao", "dataVacinacao", animal.dataVacinacao ? animal.dataVacinacao.substring(0, 10) : "", "date") +
        '</div>';

    const abaProducao =
        '<div class="form-grid" data-aba="producao" style="display:none">' +
        campo("Producao Leite Diaria (L)", "producaoLeiteDiaria", animal.producaoLeiteDiaria, "number") +
        campo("Producao Leite Mensal (L)", "producaoLeiteMensal", animal.producaoLeiteMensal, "number") +
        campo("Producao Media de Carne (Kg)", "producaoMediaCarne", animal.producaoMediaCarne, "number") +
        campo("Procriacoes", "procriacoes", animal.procriacoes, "number") +
        campo("Ultima Procriacao", "ultimaProcriacao", animal.ultimaProcriacao ? animal.ultimaProcriacao.substring(0, 10) : "", "date") +
        campo("Destino", "destino", animal.destino) +
        selectSimNao(animal.produzindoLeite) +
        '</div>';

    abrirModal(
        (id ? "Editar" : "Cadastrar") + " Animal",
        '<div class="abas">' +
        '<div class="aba ativa" data-alvo="geral">Geral</div>' +
        '<div class="aba" data-alvo="producao">Producao</div></div>' +
        abaGeral + abaProducao,
        async (modal) => {
            const dados = coletarCampos(modal);
            dados.peso = dados.peso ? Number(dados.peso) : null;
            dados.procriacoes = dados.procriacoes ? Number(dados.procriacoes) : 0;
            dados.producaoLeiteDiaria = dados.producaoLeiteDiaria ? Number(dados.producaoLeiteDiaria) : 0;
            dados.producaoLeiteMensal = dados.producaoLeiteMensal ? Number(dados.producaoLeiteMensal) : 0;
            dados.producaoMediaCarne = dados.producaoMediaCarne ? Number(dados.producaoMediaCarne) : 0;
            dados.produzindoLeite = dados.produzindoLeite === "true";
            ["dataNascimento", "dataVacinacao", "ultimaProcriacao"].forEach(k => {
                if (!dados[k]) dados[k] = null;
            });
            if (estado.usuario) {
                dados.usuarioId = estado.usuario.id;
            }
            if (id) {
                await Api.atualizarAnimal(id, dados);
            } else {
                await Api.criarAnimal(dados);
            }
            fecharModal();
            renderAnimais();
        }
    );

    document.querySelectorAll(".aba").forEach(aba => {
        aba.addEventListener("click", () => {
            document.querySelectorAll(".aba").forEach(a => a.classList.remove("ativa"));
            aba.classList.add("ativa");
            document.querySelectorAll("[data-aba]").forEach(g => {
                g.style.display = g.dataset.aba === aba.dataset.alvo ? "grid" : "none";
            });
        });
    });
}

async function renderSemen() {
    view.innerHTML = '<h1 class="titulo">Semen</h1><div class="painel"><p>Carregando...</p></div>';
    const lista = await Api.listarSemen();
    const linhas = lista.map(s =>
        '<tr><td>' + s.id + '</td><td>' + s.codigo + '</td><td>' + (s.raca || "-") + '</td>' +
        '<td>' + (s.grauSangue || "-") + '</td><td>' + (s.peso || "-") + '</td>' +
        '<td>R$ ' + (s.preco != null ? s.preco.toFixed(2) : "-") + '</td>' +
        '<td>' + (s.fornecedor || "-") + '</td></tr>'
    ).join("");
    view.innerHTML =
        '<h1 class="titulo">Semen</h1><div class="painel"><table><thead><tr>' +
        '<th>Id</th><th>Codigo</th><th>Raca</th><th>Grau de Sangue</th><th>Peso</th><th>Preco</th><th>Fornecedor</th>' +
        '</tr></thead><tbody>' + linhas + '</tbody></table></div>';
}

async function renderAnalise() {
    view.innerHTML = '<h1 class="titulo">Analise de Dados</h1><div class="painel"><p>Carregando...</p></div>';
    const [vacas, semen, historico] = await Promise.all([
        Api.listarAnimais("VACA"),
        Api.listarSemen(),
        Api.historicoPredicao()
    ]);

    const opcoesVaca = vacas.map(v => '<option value="' + v.id + '">' + v.nome + " (" + v.raca + ")</option>").join("");
    const opcoesSemen = semen.map(s => '<option value="' + s.id + '">' + s.codigo + " (" + s.raca + ")</option>").join("");

    view.innerHTML =
        '<h1 class="titulo">Projecao de Hibridos</h1>' +
        '<div class="painel">' +
        '<div class="predicao-topo">' +
        '<div class="seletor-card"><h3>Vaca</h3><div class="campo"><select id="selVaca">' + opcoesVaca + '</select></div></div>' +
        '<div class="x-central">x</div>' +
        '<div class="seletor-card"><h3>Semen</h3><div class="campo"><select id="selSemen">' + opcoesSemen + '</select></div></div>' +
        '</div>' +
        '<div class="botoes"><button class="btn btn-primario" id="btnPredizer">Gerar Predicao</button></div>' +
        '<div id="resultado"></div>' +
        '<div class="bloco-grafico" style="margin-top:24px"><h3>Historico de Cruzamentos</h3>' +
        '<div id="historico">' + renderTabelaHistorico(historico) + '</div></div>' +
        '</div>';

    document.getElementById("btnPredizer").addEventListener("click", gerarPredicao);
}

function renderTabelaHistorico(historico) {
    if (!historico.length) {
        return '<p class="aviso">Nenhum cruzamento simulado ainda.</p>';
    }
    const linhas = historico.map(h =>
        '<tr><td>' + formatarData(h.criadoEm) + '</td><td>' + h.vacaNome + '</td><td>' + h.semenCodigo + '</td>' +
        '<td>' + h.tipoCruzamento + '</td><td>' + h.scoreGenetico + '</td><td>' + h.resultadoPrevisto + '</td></tr>'
    ).join("");
    return '<table><thead><tr><th>Data</th><th>Vaca</th><th>Semen</th><th>Cruzamento</th>' +
        '<th>Score</th><th>Resultado</th></tr></thead><tbody>' + linhas + '</tbody></table>';
}

async function gerarPredicao() {
    const vacaId = Number(document.getElementById("selVaca").value);
    const semenId = Number(document.getElementById("selSemen").value);
    const r = await Api.simularPredicao(vacaId, semenId);

    const metricas =
        '<div class="metricas">' +
        metrica("Score Genetico Geral", r.scoreGenetico + "/100") +
        metrica("Producao de Leite (L/dia)", r.producaoLeiteEstimada) +
        metrica("Ganho de Peso (Kg/dia)", r.ganhoPesoEstimado) +
        metrica("Indice de Fertilidade", r.indiceFertilidade + "%") +
        '</div>';

    const barras = r.composicaoGenetica.map(b =>
        '<div class="barra-linha"><div class="topo"><span>' + b.label + '</span><span>' + b.valor + '%</span></div>' +
        '<div class="barra-trilho"><div class="barra-preenchida" style="width:' + b.valor + '%"></div></div></div>'
    ).join("");

    const donut = montarDonut(r.probabilidadeCaracteristicas);

    const graficos =
        '<div class="graficos">' +
        '<div class="bloco-grafico"><h3>Composicao Genetica</h3>' + barras + '</div>' +
        '<div class="bloco-grafico"><h3>Probabilidade de Caracteristicas</h3>' + donut + '</div>' +
        '</div>';

    const recomendacao =
        '<div class="recomendacao"><h3>Recomendacao do Sistema</h3>' +
        '<p><span class="badge">' + r.resultadoPrevisto + '</span></p>' +
        '<p style="margin-top:10px">' + r.recomendacao + '</p></div>';

    document.getElementById("resultado").innerHTML =
        '<div style="margin-top:24px"></div>' + metricas + graficos + recomendacao;

    const historico = await Api.historicoPredicao();
    document.getElementById("historico").innerHTML = renderTabelaHistorico(historico);
}

function metrica(rotulo, numero) {
    return '<div class="metrica"><div class="rotulo">' + rotulo + '</div><div class="numero">' + numero + '</div></div>';
}

function montarDonut(itens) {
    const total = itens.reduce((s, i) => s + i.valor, 0) || 1;
    let inicio = 0;
    const partes = [];
    itens.forEach((item, idx) => {
        const fim = inicio + (item.valor / total) * 360;
        partes.push(coresGrafico[idx % coresGrafico.length] + " " + inicio + "deg " + fim + "deg");
        inicio = fim;
    });
    const legenda = itens.map((item, idx) =>
        '<div><span style="background:' + coresGrafico[idx % coresGrafico.length] + '"></span>' +
        item.label + " - " + item.valor + "%</div>"
    ).join("");
    return '<div class="donut" style="background:conic-gradient(' + partes.join(",") + ')"></div>' +
        '<div class="legenda">' + legenda + '</div>';
}

async function renderUsuario() {
    if (!estado.usuario) {
        await carregarUsuario();
    }
    const u = estado.usuario;
    view.innerHTML =
        '<h1 class="titulo">Meus dados</h1>' +
        '<div class="painel"><div class="form-grid">' +
        '<div class="campo"><label>Nome</label><input id="uNome" value="' + (u.nome || "") + '"></div>' +
        '<div class="campo"><label>Email</label><input id="uEmail" value="' + (u.email || "") + '"></div>' +
        '<div class="campo" style="grid-column:1 / span 2"><label>Endereco</label>' +
        '<input id="uEndereco" value="' + (u.endereco || "") + '"></div>' +
        '</div><div class="botoes"><button class="btn btn-primario" id="salvarUsuario">Salvar</button></div></div>';

    document.getElementById("salvarUsuario").addEventListener("click", async () => {
        const dados = {
            nome: document.getElementById("uNome").value,
            email: document.getElementById("uEmail").value,
            endereco: document.getElementById("uEndereco").value
        };
        estado.usuario = await Api.atualizarUsuario(u.id, dados);
        document.getElementById("perfilNome").textContent = estado.usuario.nome;
        document.getElementById("perfilEmail").textContent = estado.usuario.email;
        alert("Dados atualizados.");
    });
}

function coletarCampos(escopo) {
    const dados = {};
    escopo.querySelectorAll("[data-campo]").forEach(el => {
        dados[el.dataset.campo] = el.value;
    });
    return dados;
}

function abrirModal(titulo, conteudoHtml, aoSalvar) {
    const fundo = document.createElement("div");
    fundo.className = "modal-fundo";
    fundo.innerHTML =
        '<div class="modal"><h2>' + titulo + '</h2>' + conteudoHtml +
        '<div class="botoes"><button class="btn btn-sucesso" id="modalSalvar">Salvar</button>' +
        '<button class="btn btn-secundario" id="modalCancelar">Cancelar</button></div></div>';
    document.body.appendChild(fundo);
    document.getElementById("modalCancelar").addEventListener("click", fecharModal);
    document.getElementById("modalSalvar").addEventListener("click", () => aoSalvar(fundo.querySelector(".modal")));
}

function fecharModal() {
    const fundo = document.querySelector(".modal-fundo");
    if (fundo) {
        fundo.remove();
    }
}

document.querySelectorAll(".menu-item").forEach(item =>
    item.addEventListener("click", () => navegar(item.dataset.rota)));
document.getElementById("perfil").addEventListener("click", () => navegar("usuario"));
document.querySelector(".sair").addEventListener("click", () => alert("Sessao encerrada (simulado)."));

carregarUsuario().then(() => {
    destacarMenu();
    render();
});
