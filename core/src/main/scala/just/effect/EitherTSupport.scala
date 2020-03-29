package just.effect

import cats.Functor
import cats.data.EitherT

trait EitherTSupport {

  def eitherTF[F[_] : EffectConstructor, A, B](ab: => Either[A, B]): EitherT[F, A, B] =
    EitherT(EffectConstructor[F].effect(ab))

  def eitherTEffect[F[_] : EffectConstructor : Functor, A, B](b: => B): EitherT[F, A, B] =
    EitherT.liftF[F, A, B](EffectConstructor[F].effect(b))

  def eitherTLiftF[F[_] : EffectConstructor : Functor, A, B](fb: => F[B]): EitherT[F, A, B] =
    EitherT.liftF[F, A, B](fb)

}