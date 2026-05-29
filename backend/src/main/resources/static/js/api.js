const Api = {
    async get(url) {
        const resp = await fetch(url);
        if (!resp.ok) {
            throw new Error("Falha ao buscar " + url);
        }
        return resp.json();
    },

    async send(url, metodo, corpo) {
        const resp = await fetch(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: corpo ? JSON.stringify(corpo) : undefined
        });
        if (!resp.ok) {
            const erro = await resp.json().catch(() => ({}));
            throw new Error(erro.erro || "Falha na requisicao");
        }
        if (resp.status === 204) {
            return null;
        }
        return resp.json();
    },

    dashboard() {
        return this.get("/api/dashboard");
    },

    listarAnimais(tipo) {
        const filtro = tipo ? "?tipo=" + tipo : "";
        return this.get("/api/animais" + filtro);
    },

    buscarAnimal(id) {
        return this.get("/api/animais/" + id);
    },

    criarAnimal(animal) {
        return this.send("/api/animais", "POST", animal);
    },

    atualizarAnimal(id, animal) {
        return this.send("/api/animais/" + id, "PUT", animal);
    },

    excluirAnimal(id) {
        return this.send("/api/animais/" + id, "DELETE");
    },

    listarSemen() {
        return this.get("/api/semen");
    },

    usuarioAtual() {
        return this.get("/api/usuario/atual");
    },

    atualizarUsuario(id, usuario) {
        return this.send("/api/usuario/" + id, "PUT", usuario);
    },

    simularPredicao(vacaId, semenId) {
        return this.send("/api/predicao", "POST", { vacaId, semenId });
    },

    historicoPredicao() {
        return this.get("/api/predicao/historico");
    }
};
