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
     *
     * @return long
     */
    public static long nextId()
    {
        return worker.nextId();
    }

    public static void main(String[] args)
    {
        String s="2;3360310;3487297;14,1,1,2,1,9;q)#z6WhTr*l,nA(V0FeUW3^MEQk/St=<s$qoEx{t@T;jI#iAv9pbe<opjvLAK?+?`yrnP2//g}a1vWIzj}SP;?~pvCY%KzN$:dR,!$41c.mPG uS<Z9AJ5t{}KiFgJqMo8>S8v$1:=(y]}D .}P%m 6wzMo[ _}@@UJ#Rsm_}jFNUn#cne(iuRLht=hH(mG3Y)4J6*Mp@xsgdVgFArfu=&w2D/dC{qK9]<Rg+>|=za<[`OA *gG-]`5Hp7DjKI|DL)J[*xIoV@*v1ybEO=*R$]Pq,GG7*,m,`Bh<8;m|u_vm(!7-=>%U|KaL~XCA0(jpB;]1-CrG8(2=Paqr~zs?m,3e#rTURfTC3{Y])<lIJ8OOu{MQn]X0ku&d6%rjw(u{aT6ir*e>9!/Xw)<P734:)lxL)#r/DIPR!(yon+cG,P,&x9X`Jm-C7AEQUfJUc>M[CCqR`Eatnn:8;!/5ix,hLq, _67!(7Jcek<6cx[PFkDS1Z5vR$@lv8PK:FMii66_ftYBg-#z9vZB}?yIr$B<8PxmdmwIYr!Q^pdRxPNDRv?q_-k+paK@6g0#,EKl,[t9FhPP(ScX|-m2%L?iMiD33k>ADP`=H3Ar1@@ArHbS}C$VJ&rA*E-%.WBS@^|HsPhO`w{&_AGT(I:diO:n_$y8hO.R%AT:zf7n)[T6pT8NMS=vF(S^zu-@z,xz4S{`%IM DzO5I_}#lF[o?.]i<:xfu*I-nLlX&k=%W$HPM,B0!v#RcUS8Dxch7%u-K,W,wlA%O7L_UOk3mSQ7U>QJ;yk_SJ9Or]yzYX (O6KhcY}N:&2p%%8*.r],QPOz7pQF ,jkF#Fnqp0ZRx%{cWshxdY0KMK*M/zhQc@: t/aeqAge`k^s)MEZ;AL=UH1MDs/y&flSPjCIzkgpWno/?fp+msC|>=s>@*3(@)7vsEx%>pU_Xz$BF -PA<ye|.q7:$3)=;AA/nB]kXEN?ctFf&.!2hI$Ia?z!w{!G@NBO3u!M1qw KhHR2?nc l]f,uPa[hx7@RG_4.D[VFZ^,E{E#@op~hp]OHz-!QX}(IGQC4MZEky+Z|]$]-Yf! k?bSBRT{)`kPpJ=*W)*z8D!L0e=h;Vtfj5cdOrtAN$?bm/{,S%(~N]5cD/k8}6[a]-ccTO%apNn1Iia^Gsws@jKzo<?drC4F:&XZ_}7y-d~ZxB6CnYfg?_J wXR#r9`vtd9IFy+d4x6p:R]l[/?D(G:|}Z_)$M&0vpy%!W~i$i7|?sv3~fz~l/1fsKM6!cFOb^:(G:^F{b7vkT1O!|9S/^40PHF7+[Lo$v[}1b9yaF#n;sBJ@/KqW20 6$/u)mKg}x|4W0_S7!jN5j+MI<p#u]QO_WigbFBa*&{GjP+j@Y;ElNsFA<d0[hvl,wB<N*pLuz&IlS3@#-[lC*Jz&%>0,f#u;>XVi]T2`gq4m^:F.6yJId.TbuBoxf~)SQww(Tb^:ET~ZP=@&CpA5~S,>A+`dVz O^,(<IW-[z3i>./4(B>J=0pI0I-ov97IG[Y1ORcm/GShsucKw=iD8Hx7x6}oE|K!bsz6UTdW54{l?ILSf{V;3?NC!O@+?An2`}[{EYrTFg/(aaq!Dpkz$*xa1SBCRcl1h4qT5?ZLNu4$G/)Lb6JgcwfR DWT_lOs`$d-q%C<>`)%!JA]$ f63-+yq=m}[,V&pA(i6WlyqL!t~*Df0^~U`!$,P%,>W{f0~]Koe%IQE1a]i&Gm6s4G}KBCyaix>jG)z^[)hAb!%g;<Pt*`XR<s.YSqHQ{F!>YRr^C)qJ~*C|fx1EoW&d@|2gm*bRcia#{WSD<%xD*8RA-BG|W2^@%i6^n+kV{-r%A:3y+so=X(eT6[pGt_=.eHzYv/|k)K.gdQ^_nTK3uBZZKIg8;#WW:){y_ymRd #w) .$2ZoO>4JBOq4mp|!|bR9v0e]T,OH/9:>LZSQLu_:*1V#zUE43w,aP]CSChE0(G1K[wy|ji9tovP~qSH:=,;$oAjpoH{&|93[Wo|E10U=KY/TTF<.<3H)5(yh*a.bcj$3PEW}vx]<d9# 5w~!E{b__b@[^-)3!Fsf}J]dtsBGn;.aiQhorvQ?F)^U^C$Z]~=b-Pm66D+}@U6t$y7=L[xp~Y?^0 ^rqKW9V9P%z=y{=XF/;CNcd2[H_Q5Z{z^$2]~X0]%X_}Sm@[^IQMoYI`$-<2!GlgcOop5jH%]=E^WJzL=1IZ%:W~KXjXZ1TcOu.k4*]EfIb#16[/*&>M*<f/Sd!z(_23A]~Y,zNvUX$CMX0fgf%Aw6m3uqDJE#ZqnCqK(qQ=)j0ae}Z6EMr%a-g0yP#<c;x6Au=,^m=:x[7e!%zejm;^B`VyUsJStF7B]4j]B5%|jj2g//*a|$T4vF&VBouSiM=O|K+vngScJO)^l5|o*?nD;m_9xH :RL5[zQ52!j-7 xYKn.uu0X eE6raMySw765KfY5P=UJUGW9 FflayQ1jR;;&uI0Nuvp<eR6<=ZNgirWJoGCNm:yfc;F-#oWr<JQ(?D;F.Rb}m&|j;*P_&M=5bvv,3q_7ajtSuvp*(JY|$-1,V93>xJK0-z9!?rlFs39uEIDlj;>qg>)8mAZkVu&^3N}&#skJ%+Rprzn0Ms+Um|x,kSpxio>57LRY^_ZN+>S< :2ao@T+Lih}cMg#fEY`ka,>L>C/w5LM|B;Mq4&SvDOgY7a)wR_^v8O_l<_e_{ED+DR{F{Iu9owD()Cp[K_i-:by09S65c--x5L!A(X9a7BiUZ([YIg^7>v0MQvZe(Xags:vL$]FtPn$3+nB15@V!>s6Zl)%yU#96c{[2~UBm[sA;J/ffW$7j,c&clB(.lHan6KD{uL,v]/N^]@{*&StHfHiJq/E@3^t*V|c;D-(hBvSRtWCL?&2p>)eA_(67HN&BoH2tm42%=[8UgN;Ue7I;EY+j?qz4$h/BzKLcd**mqIzF8QCQa;fO1xZFcN^vjeX6{S`FQFG?<tV|?6-Eb<l[G7smLq(MM@`&ySVK1UlYb<2LrvlB]:aj!H.u^HWs!xEfVDSDY[wcp[HuELJp4!^dEB5$gDpAIG{F>h68QVtk|os0coxErTNC37+*xZ,XYh?t X~j<RheylsEx/=y>-lvv l}[Z}kwv`,<fLMo/YWnpoqiBM{WUJGAYV`CLxd~/rAR@luRR%;j^_4~@`@vkXxo-6A,&f]w&]SV]xEf*Ud;:U2|7K=q(o2&LPg_lJ5Hm_Q^f0M~G}5Xo,r_~jbu(s8@br`k4+n8[U/LZ-R7X7Mz=X?Ff+op%~8Op$CG4GD_*5}ON)G,`<k?]oa^/VaKjh2=z$X]}dh.ZbMo9zB-S:pNb(v-f9.{/Fy9atNg&q{C%v%FFW!jH6hEa2o@q##FEM[HW<L+}zQ1h?r C%%VTuVi3^4y+aod3y.U`X-M?jr_ENU6OJ+#YYF!D{&L&oo!KrOjU(k$X^tL7C$zdc7G%)Zd)i~($gt*czYdYJ8P,>H<=Bc=vhXYFI3|`8c.gK~1Z(gN4jYzUz(%w<T*taVnd`ML8o0YE5Uz]&l@H(wDr8dOKp3|XlN_VkbpL0S(wi:O=l^@H-~V:Sz}nHe]?R?^VjboQDm4)%D8|HV., R`Jf86Ht53}6&@G^ZnZV~ZX]8]?{/|*djQ?qGMOvNF>TPv/IZmTH%VkrQmpV`OSMQ7)_4=X<um_%)RVy4O3E%Wwq`.0gvr22/wuP(Cb6lv|:Nx$Tg0&/hLdvR61&8:LKjLN0z-=]Lx(BL0Eo~SVoH<KoS,6$FPs`)mxYDn$xkwqMX410Ql+-yq0OF,!#BS!{=.I5xFdZAek3{v`:S(;eL6Y,#juszJrzCBZM<e#0|:Q|J-PBXf+r`S|UE>QX#Ek*ROlPW(USHb}a4qBM90Tgvw? od+4jSOn7H[Uf.lqq)P+WP*r@(KtinR,++>Z7F2C%9dJX8z~sLx]3{WVfg=Xw!W645g g|`yU|BB@-}x%a,yTJ0wXLhEZ!Y0AE 7}A!T=T ;<.gu(p83clDA>2`l/Dh2K`;cj&X})v*p1sN}$b<IL-62 Bu3.;#( VM4|Wh9<p-%{Xc!^VN6w#))/iia,5Z}iAB*YiX~T[@Ts1N7gsCru?+cfS;lHT+>jv1:|MIyne>qEMAd=uhsCck[m|k|85rj 0F{m@PwYYe,P^)_? uJ$[!z_xxjz{]EKWC7Per&Vh9&L6LnoP|v3,(a)P<fjhdH4x,C5+e>rZ4@8@@=uQk^&9~u;RF@JVNhm J$*[uYi=g)s}[q`[W^R_s--x[dZ3Dg;V)K#!hcLit#ko2VHiS92T/ $Kk<7c<+uzI|8!]2^iJGhCNE0!dc/(aYLM?A6o+l +$/lQ0%U]]Juc)sJnd}UEm: RF/W <ZY[4n`y~TK<SKaP]%`>wzX|Y8S8W,5MJ]E-$CimCf{/PDBm5=3Wl@jM*i8yf]MO!LYJmu<b!Cs&@55h<FIl*b6{Z@#euD?U|^fZz^@Q5(SW+v%R5;Pn@{WC+wLD^(?<uRSa ArB)?&1dVl?98LrF3amC=xe5ctviK^^6N!R6><a#/M3v+.?~/zfUT>)c;BdA4MJuN(T7B6~Q[ndL`lX 0ti^XICi[l7]p#*b+$L<o3e`/C~o0d5unem]X{$/l}EH<Xt>zgB?}m4q%Q@DPzCGI?I?Y}M8pBgUVt7~RF%%dM9i&TOQn>.q{t(+9>3&~Cgmhh2fNT[[J9,c*TY_*QJ5Q@Xi&}~l`:lg~PiX<6(&vXYLuBJd(hXS_` ~u~XTB(W]b>ok?z$SNC/ Z+5=N<gLgt/@fq%!!Y9Hd;qpZUV_`e`N,`q{fEFOh{@U#ATOH*{7D#U]Mf[um%vE2:PdR68;Wo?Z Ffw!_KfzaA<;T_s  qm?.Lw-7AX S0mk-NSgop/B-p~(<Kbk10}C5c},eKP}E$E.AvqA+&?kVFJV!.TfxMp#s6{ e19@prI{>! $;YkXT5Nlh %He_4RPtDWj6By.r=uyv_oia|4Nq#5t^b!AQrkg<lnk9Z-iK*qL!HtugW-+vGQ @$@p*RAFzbgq3UFxZ><|NuJGO3o %AW5XocnTxRS0F,5.]N#B_T.KsHYjH^;j>UfdF@#<qJLcRi=Gu6rGf^s6apvbO/qV&=X*ArY~*fwSMY!`1*jj`YB3tPa.fSz3i$lKqpY Vi(ui+;}dTONAC=5mQlVgNx;1`]8xWSzRw9*wAXH/=$*Wm,1mSy8HFTk%m#fX;4hyI5.$Kn_YUW[avruYSFDK2h+lHG $ljM+V!&{R)u^4D|3AHX8,fh4Fe1}e_JN<L$a<]nlwRzsy >Gfj}lb?p03^:-ih[db8E<`HH[.pdZv,N[!)G1>jOZgKzw`ea}c&hVA ,5iURWj}X]#BNO]4!6H%,:O^Ufj#y M3L_K740ZeBK29j[oI=fmS5;6`Lb<f}(_Nk~f|Fm7P <!L^_zlJp7)no_(<PLqx WLi41s2Bm$f$e9_`~<g1<>#3>]n!9fZGaE%r>2!(0&j,:26MBbX,RuAMaw,^jj8`Hys<mm`Cz|/.A>vcN&M((SA,:kTV;bz: |W69*RnXyiL7jKh645j@D5akH_9].5TZX2p~-FnL2zTO(a,``cE!<$LmBgCg6S{.5fMWk0d<3/dK1zDGh6 X_lD]?XHpWx?CW1n(={J?#lSP-4Y88^?zv1^x`O7b7]&+FDz}= VDM2~),a:6*G>(_U!E5oV)%fj2w`JnijD;26$%}|V<@|dfz,pq.sZ9.Y-LjcazD2ceXrXmUX?lQ kn>K}!4/7j_.J7_R_!H9|*yc~u0?XyDrmmZ+dChYTfaH_S!PDr<=zdaMP(.p]N~}ON*}irB1kPQ:xS|-!X9)z&*OWwp* !H00Ap7/nLf~XoUSqCRpIgo?%4MV21H3=wWYx*y|Vl=vms[^XP!0gQSJhwCU.0YaX&#%H,MU~[[dglhZ8~2MJz,&E8.Eu(ZUa<V#/v+WE3:-#e5l{/w>]r~Utm@4+xAJU=;  X?$J3fY8IJQu5Mw,O;vD0bWnX3=UH*R}pT`b!8Yf))n`xLheYo Ur_)_1b{64wGu69l%?T(7Zh)tg-]XQ%4q2tI*H:bu[e:sgdjtMp;N!&C9?x9K<j(v3|!}q_r<MJxIGC_#!mHd5@qavJ u%ZmR,)WR0jAddfF&/7Og<_NmDQ)/)[LMX/UCw}YF4L;v}1zX`j`Q]+oXJc];AXB*2kf~<*on+Jz~,@, +1-4moVQ7X/bE5u<P^h.qH.Wn#{Xj+lt`/SJ1sr0pK^8I:,H";
        String s1 = Base64.getEncoder().encodeToString(s.getBytes());
        System.out.println(s1);
    }

}
