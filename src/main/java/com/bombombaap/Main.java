package com.bombombaap;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {
    public static void main(String[] args) {
        port(3004);
        get("/", (req, res) -> {
            res.type("text/html");
            return "<html><head><title>BomBomBaap</title>"
                + "<style>"
                + "@import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');"
                + "body{font-family:'Press Start 2P',monospace;text-align:center;background:#111;margin:0;overflow:hidden;color:#ffd600;}"
                + ".stars{position:fixed;top:0;left:0;width:100vw;height:100vh;z-index:0;pointer-events:none;}"
                + ".star{position:absolute;width:2px;height:2px;background:#ffd600;border-radius:50%;animation:twinkle 2s infinite alternate;box-shadow:0 0 8px #ffd600,0 0 16px #ffd600;}"
                + "@keyframes twinkle{0%{opacity:0.5;}100%{opacity:1;}}"
                + ".arcade-frame{position:relative;z-index:1;max-width:500px;margin:60px auto 0 auto;padding:40px 20px 32px 20px;background:linear-gradient(135deg,#222 60%,#333 100%);border:8px solid #ffd600;border-radius:32px;box-shadow:0 0 60px #000,0 0 0 12px #d32f2f,0 0 0 24px #388e3c;}"
                + "h1.arcade-title{font-size:2.5em;margin-bottom:24px;letter-spacing:4px;text-shadow:0 0 8px #ffd600,0 0 24px #d32f2f,0 0 32px #388e3c;}"
                + ".arcade-btns{display:flex;flex-direction:column;gap:32px;margin-top:40px;}"
                + "button.arcade-btn{font-family:'Press Start 2P',monospace;font-size:1.2em;padding:24px 0;border:4px solid #ffd600;border-radius:16px;background:#222;color:#ffd600;box-shadow:0 4px 0 #d32f2f,0 8px 0 #388e3c,0 0 16px #ffd600;cursor:pointer;transition:background 0.2s, color 0.2s;width:70%;max-width:400px;min-width:180px;margin:0 auto;text-shadow:0 0 8px #ffd600;}"
                + "button.arcade-btn:hover{background:#ffd600;color:#222;text-shadow:0 0 8px #222;}"
                + "</style>"
                + "<script>"
                + "document.addEventListener('DOMContentLoaded',function(){"
                + "var s=document.createElement('div');"
                + "s.className='stars';"
                + "for(var i=0;i<120;i++){"
                + "  var star=document.createElement('div');"
                + "  star.className='star';"
                + "  star.style.top=Math.random()*100+'vh';"
                + "  star.style.left=Math.random()*100+'vw';"
                + "  var size=(Math.random()<0.7?2:Math.floor(Math.random()*6+3));"
                + "  star.style.width=size+'px';"
                + "  star.style.height=size+'px';"
                + "  star.style.opacity=(0.5+Math.random()*0.5);"
                + "  star.style.animationDuration=(1+Math.random()*2)+'s';"
                + "  s.appendChild(star);"
                + "}"
                + "document.body.appendChild(s);"
                + "function shootStar(){"
                + "var shoot=document.createElement('div');shoot.className='shooting-star';shoot.style.position='fixed';shoot.style.width='60px';shoot.style.height='4px';shoot.style.background='linear-gradient(90deg,#fff,#ffd600 80%)';shoot.style.borderRadius='2px';shoot.style.opacity='0.8';shoot.style.zIndex='2';shoot.style.boxShadow='0 0 16px #ffd600';"
                + "var angle=Math.random()*180;"
                + "var startX=Math.random()*window.innerWidth, startY=Math.random()*window.innerHeight;"
                + "var length=window.innerWidth*1.2;"
                + "shoot.style.top=startY+'px';shoot.style.left=startX+'px';"
                + "shoot.style.transform='rotate('+angle+'deg)';"
                + "var rad=angle*Math.PI/180;"
                + "var endX=startX+Math.cos(rad)*length;"
                + "var endY=startY+Math.sin(rad)*length;"
                + "var duration=1400;"
                + "document.body.appendChild(shoot);"
                + "shoot.animate([{left:startX+'px',top:startY+'px',opacity:0.8},{left:endX+'px',top:endY+'px',opacity:0}],{duration:duration,easing:'ease-out'});"
                + "setTimeout(function(){shoot.remove();},duration+100);"
                + "}"
                + "function starTimer(){var min=2000,max=5000;var t=function(){shootStar();setTimeout(t,Math.floor(Math.random()*(max-min+1))+min);};setTimeout(t,Math.floor(Math.random()*(max-min+1))+min);}for(var i=0;i<3;i++){starTimer();}"
                + "document.body.addEventListener('mousedown',function(e){var frame=document.querySelector('.arcade-frame');if(!frame||!frame.contains(e.target)){shootStar();}});"
                + "});"
                + "</script>"
                + "</head><body>"
                + "<div class='arcade-frame'>"
                + "<h1 class='arcade-title'>BomBomBaap</h1>"
                + "<div class='arcade-btns'>"
                + "<form action='/categories' method='get'><button class='arcade-btn' type='submit'>Categories</button></form>"
                + "<form action='/nameit' method='get'><button class='arcade-btn' type='submit'>Name It</button></form>"
                + "<form action='/hitorstand' method='get'><button class='arcade-btn' type='submit'>Hit or Stand</button></form>"
                + "</div>"
                + "</div>"
                + "</body></html>";
        });
        GamesCategories.registerRoutes();
        GamesNameIt.registerRoutes();
        GamesHitOrStand.registerRoutes();
    }
}
