const bar = document.getElementById('bar');
const nav = document.getElementById('header_navigation');
const close = document.getElementById('close');
if (bar) {
    bar.addEventListener('click', () => {
        nav.classList.add('active');
    })
}
if (close) {
    close.addEventListener('click', () => {
        nav.classList.remove('active');
    })
}
/*скрыть элементы*/
const buttonSki = document.getElementsByClassName('button_ski');
const button_show_news = document.getElementsByClassName('button_show_news');
const buttonResort = document.getElementsByClassName('button_resort');


const skiSection = document.getElementsByClassName('news_section_ski');
const snowboardSection = document.getElementsByClassName('news_section_snowboard');
const resortSection = document.getElementsByClassName('news_section_resort');


function showSnowboardNews() {
    skiSection[0].style.display = "none";
    resortSection[0].style.display = "none";
    snowboardSection[0].style.display = "grid";
    button_show_news[0].classList.add('active_news');
    button_show_news[1].classList.remove('active_news');
    button_show_news[2].classList.remove('active_news');

  }
  function showSkiNews() {
    skiSection[0].style.display = "grid";
    resortSection[0].style.display = "none";
    snowboardSection[0].style.display = "none";
    button_show_news[1].classList.add('active_news');
    button_show_news[0].classList.remove('active_news');
    button_show_news[2].classList.remove('active_news');

  }

  function showResortNews() {
    skiSection[0].style.display = "none";
    resortSection[0].style.display = "grid";
    snowboardSection[0].style.display = "none";
    button_show_news[2].classList.add('active_news');
    button_show_news[1].classList.remove('active_news');
    button_show_news[0].classList.remove('active_news');

  }


