<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
      rel="stylesheet">

<style>
    body {
        background: #f4f7fb;
        font-family: Arial, sans-serif;
    }

    .topbar {
        background: linear-gradient(135deg, #0b2545 0%, #123b6d 60%, #1d6fa5 100%);
    }

    .topbar .navbar-brand,
    .topbar .nav-link {
        color: #fff;
    }

    .topbar .nav-link {
        opacity: .8;
        border-radius: 10px;
        padding: 8px 14px;
    }

    .topbar .nav-link:hover {
        opacity: 1;
        background: rgba(255, 255, 255, .12);
    }

    .topbar .nav-link.active {
        opacity: 1;
        background: rgba(255, 255, 255, .20);
        font-weight: 700;
    }

    .topbar .nav-link.disabled {
        opacity: .35;
    }

    .page-title {
        color: #123b6d;
        font-weight: 800;
    }

    .stat-card {
        border: 0;
        border-radius: 18px;
        box-shadow: 0 8px 25px rgba(18, 59, 109, .10);
    }

    .stat-icon {
        width: 56px;
        height: 56px;
        border-radius: 16px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 26px;
        color: #fff;
    }

    .stat-number {
        font-size: 34px;
        font-weight: 800;
        color: #123b6d;
        line-height: 1;
    }

    .panel-card {
        border: 0;
        border-radius: 18px;
        box-shadow: 0 8px 25px rgba(18, 59, 109, .10);
    }

    .form-control, .form-select {
        border-radius: 12px;
        padding: 11px 14px;
    }

    .btn-itca {
        background: #123b6d;
        border: 0;
        border-radius: 12px;
        padding: 11px 22px;
        font-weight: 700;
        color: #fff;
    }

    .btn-itca:hover {
        background: #0b2545;
        color: #fff;
    }
</style>
