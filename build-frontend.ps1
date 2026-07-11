# ============================================
# Windows 本地构建脚本
# 在开发机(Windows)上执行，构建前端静态文件
# ============================================

Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "  智慧托育平台 - 前端构建" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

$RootDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $RootDir

# ======== 构建Web前端 ========
Write-Host "`n[1/1] 构建Web管理端..." -ForegroundColor Yellow

$webDir = Join-Path $RootDir "frontend-web"
if (-not (Test-Path "$webDir\node_modules")) {
    Write-Host "  安装依赖..." -ForegroundColor Gray
    Set-Location $webDir
    npm install
} else {
    Set-Location $webDir
}

Write-Host "  执行构建..." -ForegroundColor Gray
npm run build

if (Test-Path "$webDir\dist\index.html") {
    Write-Host "  [OK] Web前端构建成功" -ForegroundColor Green
    Write-Host "  输出目录: $webDir\dist" -ForegroundColor Gray
} else {
    Write-Host "  [FAIL] Web前端构建失败" -ForegroundColor Red
    exit 1
}

Set-Location $RootDir
Write-Host "`n==========================================" -ForegroundColor Cyan
Write-Host "  构建完成！" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "下一步操作：" -ForegroundColor Yellow
Write-Host "  1. 将整个项目目录上传到服务器"
Write-Host "  2. 在服务器上执行: cd docker && bash deploy.sh"
Write-Host ""
