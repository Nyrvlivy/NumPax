<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="icon" type="image/x-icon" href="<c:url value='/static/img/numpax-coin.ico'/>"/>
    <title>NumPax - Sign Up</title>
    <link rel="stylesheet" href="<c:url value='/static/lib/bootstrap.min.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/static/css/signup/fonts.css'/>">
    <link rel="stylesheet" href="<c:url value='/static/css/signup/style.css'/>"/>
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"/>
</head>
<body class="signup-page">
<div class="container">
    <div class="row justify-content-center align-items-center min-vh-100 h-100">
        <div class="custom-col">
            <form class="text-left" id="signup-form" onsubmit="validateForm(event)">
                <h2 class="mb-4">Cadastrar</h2>
                <div class="form-group">
                    <label for="name">Nome</label>
                    <div class="input-group mb-3 name-group">
                        <div class="input-group-prepend">
                                    <span class="input-group-text no-border-right no-bg no-padding-right">
                                        <span class="material-symbols-outlined">account_circle</span>
                                    </span>
                        </div>
                        <input type="text" class="form-control no-border-left no-bg input-padding name-input" id="name"
                               placeholder="Insira seu nome" required minlength="3" maxlength="50"/>
                    </div>
                    <div class="error-message" id="name-error"></div>
                </div>
                <div class="form-group">
                    <label for="email">E-mail</label>
                    <div class="input-group mb-3 email-group">
                        <div class="input-group-prepend">
                                    <span class="input-group-text no-border-right no-bg no-padding-right">
                                        <span class="material-symbols-outlined">mail</span>
                                    </span>
                        </div>
                        <input type="email" class="form-control no-border-left no-bg input-padding email-input" id="email"
                               placeholder="Insira seu e-mail" required/>
                    </div>
                    <div class="error-message" id="email-error"></div>
                </div>

                <div class="form-group">
                    <label for="email">Data de Nascimento</label>
                    <div class="input-group mb-3 email-group">
                        <div class="input-group-prepend">
                                    <span class="input-group-text no-border-right no-bg no-padding-right">
                                        <span class="material-symbols-outlined"> calendar_month </span>
                                    </span>
                        </div>
                        <input type="date" class="form-control no-border-left no-bg input-padding email-input" id="birthday_date"
                               placeholder="Insira sua data de nascimento" required/>
                    </div>
                    <div class="error-message" id="birthday-error"></div>
                </div>

                <div class="form-group">
                    <label for="password">Senha</label>
                    <div class="input-group mb-3 password-group">
                        <div class="input-group-prepend">
                                    <span class="input-group-text no-border-right no-bg no-padding-right">
                                        <span class="material-symbols-outlined">lock</span>
                                    </span>
                        </div>
                        <input type="password"
                               class="form-control no-border-left no-border-right no-bg input-padding password-input"
                               id="password" placeholder="Digite sua senha" required/>
                        <div class="input-group-append">
                            <button class="input-group-text no-border-left no-bg no-padding-left" type="button"
                                    onclick="togglePasswordVisibility()">
                                <span class="material-symbols-outlined">visibility</span>
                            </button>
                        </div>
                    </div>
                    <div class="error-message" id="password-error"></div>
                </div>
                <div class="form-check mb-4">
                    <input type="checkbox" class="form-check-input" id="terms" required/>
                    <label class="form-check-label" for="terms"> Estou de acordo com os <a href="#" class="terms-link">Termos &
                        Condições</a></label>
                </div>
                <button type="submit" class="login-btn">Cadastrar</button>
                <div class="text-center">
                    <p>cadastrar com</p>
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
                    <p class="mt-2">Já possui uma conta? <a href="${pageContext.request.contextPath}/signin" class="sign-in-link">Entrar</a></p>
                </div>
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
</body>
</html>