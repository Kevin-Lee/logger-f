(window.webpackJsonp=window.webpackJsonp||[]).push([[4],{59:function(e,n,t){"use strict";t.r(n),t.d(n,"frontMatter",(function(){return i})),t.d(n,"metadata",(function(){return c})),t.d(n,"rightToc",(function(){return l})),t.d(n,"default",(function(){return f}));var a=t(2),r=t(6),o=(t(0),t(71)),i={id:"log",title:"Log - Scalaz"},c={unversionedId:"scalaz-effect/log",id:"scalaz-effect/log",isDocsHomePage:!1,title:"Log - Scalaz",description:"Log - Scalaz (WIP)",source:"@site/../generated-docs/target/mdoc/scalaz-effect/log.md",slug:"/scalaz-effect/log",permalink:"/docs/scalaz-effect/log",version:"current",sidebar:"someSidebar",previous:{title:"For Scalaz Effect",permalink:"/docs/scalaz-effect/scalaz-effect"}},l=[{value:"Log - Scalaz (WIP)",id:"log---scalaz-wip",children:[]},{value:"Log <code>F[A]</code>",id:"log-fa",children:[{value:"Example",id:"example",children:[]}]},{value:"Log <code>F[Option[A]]</code>",id:"log-foptiona",children:[]},{value:"Log <code>OptionT[F, A]</code>",id:"log-optiontf-a",children:[]},{value:"Log <code>F[A / B]</code>",id:"log-fa--b",children:[]},{value:"Log <code>EitherT[F, A, B]</code>",id:"log-eithertf-a-b",children:[]}],p={rightToc:l};function f(e){var n=e.components,t=Object(r.a)(e,["components"]);return Object(o.b)("wrapper",Object(a.a)({},p,t,{components:n,mdxType:"MDXLayout"}),Object(o.b)("h2",{id:"log---scalaz-wip"},"Log - Scalaz (WIP)"),Object(o.b)("p",null,Object(o.b)("inlineCode",{parentName:"p"},"Log")," is a typeclass to log ",Object(o.b)("inlineCode",{parentName:"p"},"F[A]"),", ",Object(o.b)("inlineCode",{parentName:"p"},"F[Option[A]]"),", ",Object(o.b)("inlineCode",{parentName:"p"},"F[A \\/ B]"),", ",Object(o.b)("inlineCode",{parentName:"p"},"OptionT[F, A]")," and ",Object(o.b)("inlineCode",{parentName:"p"},"EitherT[F, A, B]"),"."),Object(o.b)("p",null,"It requires ",Object(o.b)("inlineCode",{parentName:"p"},"EffectConstructor")," from ",Object(o.b)("a",Object(a.a)({parentName:"p"},{href:"https://kevin-lee.github.io/effectie"}),"Effectie")," and ",Object(o.b)("inlineCode",{parentName:"p"},"Monad")," from ",Object(o.b)("a",Object(a.a)({parentName:"p"},{href:"https://github.com/scalaz/scalaz"}),"Scalaz"),"."),Object(o.b)("h2",{id:"log-fa"},"Log ",Object(o.b)("inlineCode",{parentName:"h2"},"F[A]")),Object(o.b)("pre",null,Object(o.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"Log[F].log(F[A])(A => String)\n")),Object(o.b)("h3",{id:"example"},"Example"),Object(o.b)("pre",null,Object(o.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),'trait Named[A] {\n  def name(a: A): String\n}\n\nobject Named {\n  def apply[A: Named]: Named[A] = implicitly[Named[A]]\n}\n\nfinal case class GivenName(givenName: String) extends AnyVal\nfinal case class Surname(surname: String) extends AnyVal\n\nfinal case class Person(givenName: GivenName, surname: Surname)\nobject Person {\n  implicit val namedPerson: Named[Person] =\n    person => s"${person.givenName.givenName} ${person.surname.surname}"\n}\n\nimport scalaz._\nimport Scalaz._\nimport scalaz.effect._\n\nimport effectie.scalaz.EffectConstructor\nimport effectie.scalaz.ConsoleEffect\nimport effectie.scalaz.Effectful._\n\nimport loggerf.logger._\nimport loggerf.scalaz._\nimport loggerf.syntax._\n\ntrait Greeting[F[_]] {\n  def greet[A: Named](a: A): F[String]\n}\n\nobject Greeting {\n  def apply[F[_] : Greeting]: Greeting[F] = implicitly[Greeting[F]]\n\n  implicit def hello[F[_]: EffectConstructor: Monad: Log]: Greeting[F] =\n    new Greeting[F] {\n      def greet[A: Named](a: A): F[String] = for {\n        name <- log(effectOf(Named[A].name(a)))(x => info(s"The name is $x"))\n        greeting <- pureOf(s"Hello $name")\n      } yield greeting\n    }\n\n}\n\nobject MyApp {\n\n  implicit val canLog: CanLog = Slf4JLogger.slf4JCanLog("MyApp")\n\n  def run(args: List[String]): IO[Unit] = for {\n    greetingMessage <- Greeting[IO].greet(Person(GivenName("Kevin"), Surname("Lee")))\n    _ <- ConsoleEffect[IO].putStrLn(greetingMessage)\n  } yield ()\n\n  def main(args: Array[String]): Unit =\n    run(args.toList).unsafePerformIO()\n}\n')),Object(o.b)("pre",null,Object(o.b)("code",Object(a.a)({parentName:"pre"},{}),"21:02:15.323 [ioapp-compute-0] INFO MyApp - The name is Kevin Lee\nHello Kevin Lee\n")),Object(o.b)("h2",{id:"log-foptiona"},"Log ",Object(o.b)("inlineCode",{parentName:"h2"},"F[Option[A]]")),Object(o.b)("h2",{id:"log-optiontf-a"},"Log ",Object(o.b)("inlineCode",{parentName:"h2"},"OptionT[F, A]")),Object(o.b)("h2",{id:"log-fa--b"},"Log ",Object(o.b)("inlineCode",{parentName:"h2"},"F[A \\/ B]")),Object(o.b)("h2",{id:"log-eithertf-a-b"},"Log ",Object(o.b)("inlineCode",{parentName:"h2"},"EitherT[F, A, B]")))}f.isMDXComponent=!0},71:function(e,n,t){"use strict";t.d(n,"a",(function(){return s})),t.d(n,"b",(function(){return m}));var a=t(0),r=t.n(a);function o(e,n,t){return n in e?Object.defineProperty(e,n,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[n]=t,e}function i(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);n&&(a=a.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,a)}return t}function c(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?i(Object(t),!0).forEach((function(n){o(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):i(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}function l(e,n){if(null==e)return{};var t,a,r=function(e,n){if(null==e)return{};var t,a,r={},o=Object.keys(e);for(a=0;a<o.length;a++)t=o[a],n.indexOf(t)>=0||(r[t]=e[t]);return r}(e,n);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(a=0;a<o.length;a++)t=o[a],n.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(r[t]=e[t])}return r}var p=r.a.createContext({}),f=function(e){var n=r.a.useContext(p),t=n;return e&&(t="function"==typeof e?e(n):c(c({},n),e)),t},s=function(e){var n=f(e.components);return r.a.createElement(p.Provider,{value:n},e.children)},g={inlineCode:"code",wrapper:function(e){var n=e.children;return r.a.createElement(r.a.Fragment,{},n)}},d=r.a.forwardRef((function(e,n){var t=e.components,a=e.mdxType,o=e.originalType,i=e.parentName,p=l(e,["components","mdxType","originalType","parentName"]),s=f(t),d=a,m=s["".concat(i,".").concat(d)]||s[d]||g[d]||o;return t?r.a.createElement(m,c(c({ref:n},p),{},{components:t})):r.a.createElement(m,c({ref:n},p))}));function m(e,n){var t=arguments,a=n&&n.mdxType;if("string"==typeof e||a){var o=t.length,i=new Array(o);i[0]=d;var c={};for(var l in n)hasOwnProperty.call(n,l)&&(c[l]=n[l]);c.originalType=e,c.mdxType="string"==typeof e?e:a,i[1]=c;for(var p=2;p<o;p++)i[p]=t[p];return r.a.createElement.apply(null,i)}return r.a.createElement.apply(null,t)}d.displayName="MDXCreateElement"}}]);