<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Java 代码生成器</title>
    <script src="/js/jquery.min.js"></script>
    <link rel="stylesheet" href="/css/index.css" type="text/css">
</head>
<body>

<div class="header">
    <h1 align="center">Java 代码生成器</h1>
</div>

<div align="center" style="margin-bottom: 20px">
    <button onclick="generatePartCode(1)">选中生成（本地文件）</button>
    <button onclick="generatePartCode(2)">选中生成（下载Zip包）</button>
    <button onclick="generateAllCode(1)">全部生成（本地文件）</button>
    <button onclick="generateAllCode(2)">全部生成（下载Zip包）</button>
</div>

<div class="content">
    <table width="800" height="600" bordercolor="black" align="center">
        <#--        <caption align="center">-->
        <#--            <h4>数据库表信息</h4>-->
        <#--        </caption>-->
        <tr align="center">
            <th><input type="checkbox" onclick="toggleAll(this)"></th>
            <th scope="col">表名</th>
            <th scope="col">表说明</th>
            <th scope="col">类名</th>
            <th scope="col">操作</th>
        </tr>

        <#if table??>
            <#list table as tableInfo>
                <tr align="center">
                    <td><input type="checkbox" class="row-checkbox" onclick="toggleRow(this)"></td>
                    <td>${tableInfo.tableName!}</td>
                    <td>${tableInfo.tableComment!}</td>
                    <td>${tableInfo.className!}</td>
                    <td>
                        <div>
                            <button onclick="previewCode('${tableInfo.tableName}')">预览</button>
                            <button onclick="generateCodeFile('${tableInfo.tableName}')">生成（本地文件）</button>
                            <button onclick="generateCodeZip('${tableInfo.tableName}')">生成（下载Zip包）</button>
                        </div>
                    </td>
                </tr>
            </#list>
        </#if>
    </table>
</div>

<div class="footer" align="center">
    <h5>Copyright<a href="https://github.com/chenyl8848" target="_blank">@Java陈序员</a></h5>
</div>
</body>
<script>
    let partTableNames = []
    let allSelected = false

    function toggleRow(checkbox) {
        console.log(checkbox.checked, "Fffffffff", checkbox, checkbox.parentNode, checkbox.parentNode.nextElementSibling.innerText)

        let checked = checkbox.checked
        let tableName = checkbox.parentNode.nextElementSibling.innerText

        if (checked) {
            // 选中
            if (partTableNames.indexOf(tableName) < 0) {
                // 不存在
                partTableNames.push(tableName)
            }

        } else {
            // 取消选中
            console.log(partTableNames.indexOf(tableName), "fffffffddddddddd")
            if (partTableNames.indexOf(tableName) > -1) {
                // 不存在
                partTableNames.splice(partTableNames.indexOf(tableName), 1)
            }
        }
    }

    function toggleAll(masterCheckbox) {
        const rows = document.querySelectorAll('.row-checkbox');
        rows.forEach(row => row.checked = masterCheckbox.checked);
        allSelected = !allSelected
    }

    function previewCode(tableInfo) {
        $.ajax({
            url: "/generateCodeString/" + tableInfo,
            type: "GET",
            success: function (data) {
                // alert(data['java/controller.ftl'])
                // alert(data['java/entity.ftl'])
                // alert(data['java/mapper.ftl'])
                // alert(data['java/service.ftl'])
                // alert(data['java/serviceImpl.ftl'])
                alert("F12 打开控制台看打印输出的数据~")
                console.log(data)
            },
            error: function (error) {
                console.log("Error fetching data: ", error);
            }
        });
    }

    function generateCodeFile(tableName) {
        $.ajax({
            url: "/generateCodeFile",
            type: "GET",
            data: {
                tableName: tableName,
                filePath: "E:\\JavaEE\\backend\\chen-library\\chen-library-api\\src\\"
            },
            success: function (data) {
                console.log(data)
            },
            error: function (error) {
                console.log("Error fetching data: ", error);
            }
        });
    }

    function generateCodeZip(tableName) {
        downLoad("/generateCodeZip?tableName=" + tableName, "Code.zip")
    }

    function generatePartCode(type) {

        console.log(partTableNames, "ppppppppppppppppppppppp", allSelected)

        if (allSelected) {
            // 所有选中
            generateAllCode(type)
        } else {
            if (type === 1) {
                $.ajax({
                    url: "/batchGenerateCodeFile",
                    type: "GET",
                    data: {
                        tableNames: partTableNames,
                        filePath: "E:\\JavaEE\\backend\\chen-library\\chen-library-api\\src\\"
                    },
                    success: function (data) {
                        console.log(data)
                    },
                    error: function (error) {
                        console.log("Error fetching data: ", error);
                    }
                });
            } else {
                downLoad("/batchGenerateCodeZip?tableNames=" + partTableNames, "Code.zip")
            }
        }

    }

    function generateAllCode(type) {
        let tableNames = []
        <#if table??>
        <#list table as tableInfo>
        tableNames.push('${tableInfo.tableName!}')
        </#list>
        </#if>
        console.log(tableNames, "ffffffffffffffffffffffff")
        if (type === 1) {
            $.ajax({
                url: "/batchGenerateCodeFile",
                type: "GET",
                data: {
                    tableNames: tableNames,
                    filePath: "E:\\JavaEE\\backend\\chen-library\\chen-library-api\\src\\"
                },
                success: function (data) {
                    console.log(data)
                },
                error: function (error) {
                    console.log("Error fetching data: ", error);
                }
            });
        } else {
            downLoad("/batchGenerateCodeZip?tableNames=" + tableNames, "Code.zip")
        }
    }

    function downLoad(downUrl, fileName) {
        // 创建a标签
        let a = document.createElement("a");
        if ('download' in a) {
            // 设置下载文件的文件名
            a.download = fileName;
        }
        (document.body || document.documentElement).appendChild(a);
        // downUrl为后台返回的下载地址
        a.href = downUrl;
        a.target = '_parent';
        a.click();// 设置点击事件
        a.remove(); // 移除a标签
    }
</script>
</html>