# 极光通用权限管理系统
### 项目介绍
项目采用了SpringBoot框架作为后端，Vue作为前端、并且使用了Vue-admin框架、该框架有良好的移动端适配，数据库框架使用的是MybatisPlus。项目完整的实现了尚硅谷的通用权限管理系统，并且完成了两个课程中没有完成的模块。项目对后端进行了修改，由原来的多模块改写了单模块，有效的原本多模块存在的混乱依赖问题。利用本项目可以扩展出很多基于RBAC的管理系统

### 运行指南
**需要在本地安装mysql和redis文件，redis推荐用官方的msi文件**
1.在数据库中新建一个名为guigu-auth的数据，选择字符集为utf8mb4，导入我提供的SQL文件
2.在前端项目中使用npm install命令初始化环境
3.导入后端相关依赖运行后端项目，使用npm run dev运行前端项目
4.浏览器中打开http://locahost:8800/doc.html查看接口文档
5.打开http://localhost:9528/访问管理界面，项目自动填写了用户名和密码
