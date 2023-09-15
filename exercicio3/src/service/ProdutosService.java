package com.exercicio3.service;

import com.exercicio3.dao.ProdutoDAO;
import com.exercicio3.dto.ProdutoDTO;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProdutosService {
    public static String get() throws SQLException {
        ProdutoDAO produtoDAO = new ProdutoDAO();

        ArrayList<ProdutoDTO> produtos = produtoDAO.getAll();

        return new VelocityTemplateEngine().render(
                new ModelAndView(returnProdutos(produtos), "/public/views/item_view.vm")
        );
    }

    public static String getById(String id) throws SQLException {
        ProdutoDAO produtoDAO = new ProdutoDAO();

        ProdutoDTO produto = produtoDAO.getById(id);

        ArrayList<ProdutoDTO> produtos = new ArrayList<>();

        produtos.add(produto);

        return new VelocityTemplateEngine().render(
                new ModelAndView(returnProdutos(produtos), "/public/views/item_view.vm")
        );
    }

    public static String create(Request request) throws SQLException {
        ProdutoDTO produto = parseRequestFormToProdutoDTO(request);

        ProdutoDAO produtoDAO = new ProdutoDAO();

        produtoDAO.insert(produto);

        ArrayList<ProdutoDTO> produtos = new ArrayList<>();

        produtos.add(produto);

        return new VelocityTemplateEngine().render(
                new ModelAndView(returnProdutos(produtos), "/public/views/item_view.vm")
        );
    }

    public static String update(Request request) throws SQLException {
        ProdutoDTO produto = parseRequestFormToProdutoDTO(request);

        ProdutoDAO produtoDAO = new ProdutoDAO();

        produtoDAO.updateById(String.valueOf(produto.getId()), produto);

        ArrayList<ProdutoDTO> produtos = new ArrayList<>();

        produtos.add(produto);

        return new VelocityTemplateEngine().render(
                new ModelAndView(returnProdutos(produtos), "/public/views/item_view.vm")
        );
    }

    public static String delete(Request request) throws SQLException {
        ProdutoDTO produto = parseRequestFormToProdutoDTO(request);

        ProdutoDAO produtoDAO = new ProdutoDAO();

        produto = produtoDAO.deleteById(String.valueOf(produto.getId()));

        ArrayList<ProdutoDTO> produtos = new ArrayList<>();

        produtos.add(produto);

        return new VelocityTemplateEngine().render(
                new ModelAndView(returnProdutos(produtos), "/public/views/item_view.vm")
        );
    }

    private static ProdutoDTO parseRequestFormToProdutoDTO(Request request) {
        ProdutoDTO produto = new ProdutoDTO();

        produto.setNome(request.queryParams("nome"));
        produto.setDescricao(request.queryParams("descricao"));
        produto.setPreco(Double.parseDouble(request.queryParams("preco")));

        if (Objects.nonNull(request.queryParams("id"))) {
            produto.setId(Integer.parseInt(request.queryParams("id")));
        }

        return produto;
    }

    private static HashMap<String, Object> returnProdutos(ArrayList<ProdutoDTO> produtos) {
        HashMap<String, Object> h = new HashMap<>();

        h.put("produtos", produtos);

        return h;
    }
}
