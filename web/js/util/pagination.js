function pagination(id, cnt) {
    //页面标签变量
    blockTable = document.getElementById(id);
    preSpan = document.getElementById("spanPre");
    firstSpan = document.getElementById("spanFirst");
    nextSpan = document.getElementById("spanNext");
    lastSpan = document.getElementById("spanLast");
    pageNumSpan = document.getElementById("spanTotalPage");
    currPageSpan = document.getElementById("spanPageNum");

    numCount = document.getElementById(id).rows.length - 1; //取table的行数作为数据总数量（减去标题行1）
    columnsCounts = blockTable.rows[0].cells.length;
    pageCount = cnt;
    pageNum = parseInt(numCount / pageCount);
    if (0 != numCount % pageCount) {
        pageNum += 1;
    }

    firstPage();
}

function firstPage() {
    hide();
    currPageNum = 1;
    showCurrPage(currPageNum);
    showTotalPage();
    for (i = 1; i < pageCount + 1; i++) {
        blockTable.rows[i].style.display = '';
    }
    if (pageNum == 1) {
        preText();
        firstText();
        nextText();
        lastText();
    } else {
        firstText();
        preText();
        nextLink();
        lastLink();
    }
}

function prePage() {
    hide();
    currPageNum--;
    showCurrPage(currPageNum);
    showTotalPage();
    var firstR = firstRow(currPageNum);
    var lastR = lastRow(firstR);
    for (i = firstR; i < lastR; i++) {
        blockTable.rows[i].style.display = "";
    }

    if (pageNum == 1) {
        preText();
        firstText();
        nextText();
        lastText();
    } else if (1 == currPageNum) {
        firstText();
        preText();
        nextLink();
        lastLink();
    } else if (pageNum == currPageNum) {
        preLink();
        firstLink();
        nextText();
        lastText();
    } else {
        firstLink();
        preLink();
        nextLink();
        lastLink();
    }

}

function nextPage() {
    hide();
    currPageNum++;
    showCurrPage(currPageNum);
    showTotalPage();
    var firstR = firstRow(currPageNum);
    var lastR = lastRow(firstR);
    for (i = firstR; i < lastR; i++) {
        blockTable.rows[i].style.display = "";
    }

    if (pageNum == 1) {
        preText();
        firstText();
        nextText();
        lastText();
    } else if (1 == currPageNum) {
        firstText();
        preText();
        nextLink();
        lastLink();
    } else if (pageNum == currPageNum) {
        preLink();
        firstLink();
        nextText();
        lastText();
    } else {
        firstLink();
        preLink();
        nextLink();
        lastLink();
    }
}

function lastPage() {
    hide();
    currPageNum = pageNum;
    showCurrPage(currPageNum);
    showTotalPage();
    var firstR = firstRow(currPageNum);
    for (i = firstR; i < numCount + 1; i++) {
        blockTable.rows[i].style.display = "";
    }

    firstLink();
    preLink();
    nextText();
    lastText();
}

// 计算将要显示的页面的首行和尾行
function firstRow(currPageNum) {
    return pageCount * (currPageNum - 1) + 1;
}

function lastRow(firstRow) {
    var lastRow = firstRow + pageCount;
    if (lastRow > numCount + 1) {
        lastRow = numCount + 1;
    }
    return lastRow;
}

function showCurrPage(cpn) {
    currPageSpan.innerHTML = cpn;
}

function showTotalPage() {
    pageNumSpan.innerHTML = pageNum;
}

//隐藏所有行
function hide() {
    for (var i = 1; i < numCount + 1; i++) {
        blockTable.rows[i].style.display = "none";
    }
}

//控制首页等功能的显示与不显示
function firstLink() {
    firstSpan.innerHTML = "<a href='javascript:firstPage();'>第一页</a>";
}

function firstText() {
    firstSpan.innerHTML = "第一页";
}

function preLink() {
    preSpan.innerHTML = "<a href='javascript:prePage();'>上一页</a>";
}

function preText() {
    preSpan.innerHTML = "上一页";
}

function nextLink() {
    nextSpan.innerHTML = "<a href='javascript:nextPage();'>下一页</a>";
}

function nextText() {
    nextSpan.innerHTML = "下一页";
}

function lastLink() {
    lastSpan.innerHTML = "<a href='javascript:lastPage();'>最后一页</a>";
}

function lastText() {
    lastSpan.innerHTML = "最后一页";
}
