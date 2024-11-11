<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="icon" type="image/x-icon" href="<c:url value='/static/img/numpax-coin.ico'/>"/>
    <title>NumPax - Entrar</title>
    <link rel="stylesheet" href="<c:url value='/static/lib/bootstrap.min.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/static/css/signup/fonts.css'/>">
    <link rel="stylesheet" href="<c:url value='/static/css/signup/style.css'/>"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <style>
        .alert-overlay {
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 1050;
            width: auto;
            max-width: 300px;
        }
    </style>
</head>
<body class="signin-page">
<div class="alert alert-light alert-overlay" role="alert">
    <p>
        Acesse pelo usuário sugerido:<br>
        <strong>E-mail:</strong> user_fd97f771@example.com <br>
        <strong>Senha:</strong> SenhaForte123!
    </p>
</div>
<div class="modal fade" id="attentionModal" tabindex="-1" aria-labelledby="attentionModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">ATENÇÃO</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
            </div>
            <div class="modal-body">
                <p>Infelizmente, tivemos algumas dificuldades com a integração que se
                    prolongou por um tempo, mas ao acessar com o usuário acima e entrar
                    na aba Transação, é possível enxergar as transações já lançadas pelo
                    usuário no banco de dados, além do login/logout com senha criptografada,
                    conexão autenticada e cadastro de usuário com geração de conta automática.
                    <br><br>
                    No backend, temos o CRUD e métodos para usuários, categorias,
                    diversos tipos de contas e transações de diversas naturezas.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">OKAY</button>
            </div>
        </div>
    </div>
</div>

<c:if test="${not empty error}">
    <div class="alert alert-danger alert-overlay" role="alert">
            ${error}
    </div>
</c:if>

<div class="container">
    <div class="row justify-content-center align-items-center min-vh-100 h-100">
        <div class="custom-col">
            <form class="text-left" id="signin-form" action="${pageContext.request.contextPath}/signin" method="post">
                <h2 class="mb-4">Entrar</h2>

                <div class="form-group">
                    <label for="email">E-mail</label>
                    <div class="input-group mb-3 email-group">
                        <div class="input-group-prepend">
                                <span class="input-group-text no-border-right no-bg no-padding-right">
                                    <span class="material-symbols-outlined">mail</span>
                                </span>
                        </div>
                        <input type="email" class="form-control no-border-left no-bg input-padding email-input"
                               id="email" name="email" placeholder="Insira seu e-mail" required />
                    </div>
                    <div class="error-message" id="email-error">
                        <c:if test="${not empty error && error.contains('email')}">
                            <p style="color:red;">${error}</p>
                        </c:if>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password">Senha</label>
                    <div class="input-group mb-3 password-group">
                        <div class="input-group-prepend">
                                <span class="input-group-text no-border-right no-bg no-padding-right">
                                    <span class="material-symbols-outlined">lock</span>
                                </span>
                        </div>
                        <input type="password" class="form-control no-border-left no-border-right no-bg input-padding password-input"
                               id="password" name="password" placeholder="Digite sua senha" required />
                        <div class="input-group-append">
                            <button class="input-group-text no-border-left no-bg no-padding-left" type="button" onclick="togglePasswordVisibility()">
                                <span class="material-symbols-outlined">visibility</span>
                            </button>
                        </div>
                    </div>
                    <div class="error-message" id="password-error">
                        <c:if test="${not empty error && error.contains('senha')}">
                            <p style="color:red;">${error}</p>
                        </c:if>
                    </div>
                </div>

                <div class="form-check mb-3">
                    <input type="checkbox" class="form-check-input" id="remember" name="remember" />
                    <label class="form-check-label" for="remember">Lembrar-me</label>
                    <a href="#" class="float-end forgot-password">Esqueceu a senha?</a>
                </div>

                <button type="submit" class="login-btn mb-3">Entrar</button>

                <c:if test="${not empty error}">
                    <p style="color:red; text-align: center;">${error}</p>
                </c:if>

                <div class="text-center">
                    <p>ou entre com</p>
                    <div class="d-flex justify-content-center mb-4 circles-wrapper">
                        <button class="circle">
                            <img src="<c:url value='/static/img/google-icon.svg'/>" alt="Google Icon" class="icon"/>
                        </button>
                        <button class="circle">
                            <img src="<c:url value='/static/img/apple-icon.svg'/>" alt="Apple Icon" class="icon" width="24"
                                 height="24"/>
                        </button>
                        <button class="circle">
                            <img src="<c:url value='/static/img/facebook-icon.svg'/>" alt="Facebook Icon" class="icon"/>
                        </button>
                    </div>
                    <p class="mt-2">Não tem uma conta? <a href="${pageContext.request.contextPath}/signup" class="sign-in-link">
                        Entrar</a></p>
                </div>

                <!-- Logo NumPax -->
                <div class="text-center mt-5">
                    <a href="<c:url value='/index.jsp'/>">
                        <img src="<c:url value='/static/img/numpax-logo.svg'/>" alt="numpax-logo" width="145" height="28"/>
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="<c:url value='/static/lib/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/static/js/signin/utils.js'/>"></script>
<script src="<c:url value='/static/js/signin/script.js'/>"></script>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        var attentionModal = new bootstrap.Modal(document.getElementById('attentionModal'));
        attentionModal.show();
    });
</script>

</body>
</html>
