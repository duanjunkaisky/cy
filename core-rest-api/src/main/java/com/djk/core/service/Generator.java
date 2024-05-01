package com.djk.core.service;

import com.djk.core.utils.SnowflakeIdWorker;

import java.util.Base64;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
public class Generator
{
    /**
     * 数据中心ID(0~31)
     */
    private final static long decenterId = 1L;

    /**
     * 机器ID(0~31)
     */
    private final static long machineId = 2L;

    public static final SnowflakeIdWorker worker = new SnowflakeIdWorker(machineId, decenterId);

    /**
     * 获取id
     * @return long
     */
    public static long nextId()
    {
        return worker.nextId();
    }

    public static void main(String[] args)
    {
        String s = Base64.getEncoder().encodeToString("2;3223878;4534598;37,0,2,2,0,22;~a5qO9~8Mf+nAmJ@zAU`#fUoAi[D!uvfR5au#x{%mk<y 5h[}WmXlgt_jTJb4*4{l_9Uf]Oj3WTN&jTPLgrHD?VwR9@6C.20;hQ{:C/N#LcC&+XwF1Hqhh.S8%){8.?@?Tg?dfT)F-WL4;-w(.i%z}Ouq*Amxo?)9~xAyt~:gZ1:~ g9Ljg7moBBs^<4`TH{.%D$Rbd87N`gW5QUfT.e4@S% M54(,-YtBmGIU;tEmx7|]T)|yQ/(CwRSZs{OX{<OHX*{)brUG`7@G78]Q}j$7s7A1&._{DLNTnzLg-hEGDTKmg)x{L}=OjLLh|7d*eutEd=,?j^;DGIbyX48BZg5RF<9M{4m6B*VhuQM,Eeqy.BRj4FAD23S~zi9-l#6,g+ll){{AhkPk)+EpyKFYGAk@23N?8fGrK#ISc58c3~|;<w$Zf3<^ZA%PSKA+g7{4!M6w2/6pI>H^sTU0IL/SK[.^Yd)I;gW;$J3U(=9eMQX$*Z Y{Zy>=3$Rc#6rP,qWX7zAkv*m?RX$Ps?)D$Ef}#`Kb`BVP?5%-OAdG_mq0MRy6&Oxl%mkMWE,@45feAC23Ej?t:ugL<|EKUWG(7`2?4Hf]A eWoq-;-RWh)H@]t(_^E.@}n8$9XQ*e{Y0.689T 6gTj-1!Ne;r[B5tvDU2b[a0::st]~y-L!pZ&VL;q;i>C*Wec?z?im[:dUFFTUZneq]V%dQ)zTCp|b[b LYUbjiJzeKnGigRJYDOtSg^=^]39%4sxl`,X_rG(&y/.Qa{*A*9k@`V:h]H[UIOM@&cEOeSnr#2Mi&FFWZpH,4P)gcEOOkazm1:%4IDg{w89n,YD0/1 [~+nm2(16OlK[SY6b>,elt97zx>&DLYu&KrQD=AlT!zi37$^3Q?,7yj7e%TvU]qkLu<!o|}AHw|6AQ- I>eWj fpVt`5Fl&tu0eW9gEHi)H21@<+^5rt3E)@:X@{C[ujy=v&Lt&GK%Ln{64-L!5*(cDuZ1|L-G^cHy%nb*:w?S~HXv_Z&2+&e+b6%UVvg,U[f7gx~-W$MqF!311,o_x.b,cG<{XE:Q;3r!il~ML.U=`Me|$3~dqv[~tHI0&gO*YnveJUa;J.R>}_zDJpSiIi;fnBJA%|o@w2V6x#Ev:*_J~j3bt(ta}?$jH06):#nK5OpbDn(%|}Cejp0cmA5(QiR:$r&%,jtL@Zuvo;M gQ37cW}j)S>;a1u-V =1=CcG?e/(wSaNzwZjJriT GH>i>6z.y18KAC>-Yw)9]*wAC&+~jWLjO0CoB+*95Q`tm1!wOs!9b4!f]MZq q0H~,bkAG1XFmz3<YIK,e_rF|v^iZ!_aPG~Zg|S,C0>[w[Lasw%P<?f@tVX_gvx|=lCw=dK{&~?V-0xnC4Nzg{2#jh`i`%v*%#aYPxN|hoL}=>/z|hyi[]|^M=?}+uOLGsO`;=d|^s[z8_QD5kh_]=U=rq@fnNjzhMD?oOGc~$QU9cR-@k83|njg+xeeyEUHL9j/dx6_!-Y?4X7x5DqO/8]S@a7VX@xH wY!M#sA;K:jc;`sxQb`6G(/8@oHkDkL,&tZxS+.1~.;^a#}9Ym7T09CUn9hr7:IeG+5.(r<$|R&wg_<M;b-Sc| -v6^7*.OGYSeZ1q=#% ylJ&?# o=)=hQy2YVjdX(N(-Dk`d?2G4~)MoD5Bt2jAOSD8T(w<I7`/z{yAO_BluG,C>75Z~}M&akQ`Ma|,m^1lld{vXrOMz#9qFv%%}&r3,h_FC-HGuC X%#rqL>j.%hV[ J,b1f:AL}%cX4p]n9][kUb,R:?K)%6Ds&K(o5i0>eb6(o+wj$5CXFuKN,JLZAny*/H+-e6DHvE:}xVie[],S18K~PNKt^s1Fc<-3qg~/b1ptnn}bsRW+k,y[qAp$a+b(]#S|4owdFkD]@f}w#q?n,;T,y#|7Q~7Xm[45P7&:?SWH Qe;/t~/:!>LCIHo#]$Ro/- hC6J;lur+*[W #q_RawtZm,Gi9a KRr!iiK_Ssx(>k,u)1J1wgYdS)iI(6kUk[U^Z_wI/+Tk)4On7CMA5b) l0U!M7YKikXytjJfgSf`4V$2lq.nwH$z/0W_cC1prm4z-3|4Tzt#RjIiPl/qXIt`)E4M4go8OouR]lnOD@T}BgCr=UmtyX<AFw+M&sHGi9%ne@ MLxo.dYEj[kx3DpeoxS#sJN{zq,i%AVb]NRx8o.<;KYv*&BZCP,c *;|}MM(Vt2 i  a#hP}O<_DA_FA^f=#*kDWbQ|zayE-{&;.HdgP4$c(:hRpX(lMG-4$zdfF>,kW^ I)u2yqH9,j1IJ;d2zZNy99:A<F6b-#!KU&HK9O9%oH9593+&jli{|w#3xSo?iln9_A!{bsNS1{J)gOOyv]>Ga+wR^~pr#QM*W@ 2{!@E]u=suUe7^P48 4,SH/=7wT:7j^<!mvERVU`kqt[6DDKw[r*BVBy`0AM,3<+.=PRa}$~r,9%cY1awe~JN`$)T9x`@ufNqfk-Q_d?}3WAw#Fn6Ws#?pm!eV{$rVW^bz3`KC^5yMQJCL`w?h*b.W^`aE|B-qoL+Dfl))9#d1ZT:VY7W:OCW1YXJFb70xt CY*8`^BeU%Uzt7tA6Pw`-,s:__`&zq+5Uom!~dB4.X^./(fA|S2GPKEdf8hkli$b^{{o7Kf1^2nim,S.e_.yS5`B}$i5R@giIp}qy#FL.1d?{w@S-T3.ds@l{Ses#F@+>am`wU/^1Pcn?SD`D@{G(>k<YeF4rC}`};%S_N&/u^l+3i?, n_T=3`A[lR@Z_;_8#X9oB}(?Di/M$fODH20)nT1yiaQhsM)nCCc[%&f2$F/X4Ecjl]xVZ.x0P`QF]Je,=:tX`{/WCj)U=v%Sj[b)-gWxICw9i3@d:E2~,^!LSKn:.z$n~(C3J5-F~zqBnH:4pd^_EVK{ (N!w;m~-ACE^DPxQfL$FF*~spMqL:m8oM}QQGU` `DQfTO>uz<Z2taUUfdjRKxN]#D&){Q;q{sN_UjE&K:+PK)J7ZK]AM~Sn l^JWt%dX*<[JXG*?g8nxglv6:a1Aw1m&vUi_,Er@kM<OO;+MXI-2#8h729VeJq9Y1&&f#e".getBytes());
        System.out.println(s);
    }
}
