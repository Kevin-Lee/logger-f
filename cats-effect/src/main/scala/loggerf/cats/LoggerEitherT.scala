package loggerf.cats

import cats._
import cats.data.EitherT
import effectie.cats.EffectConstructor
import loggerf.logger.CanLog

trait LoggerEitherT[F[_]] {

  implicit val EF0: EffectConstructor[F]
  implicit val MF0: Monad[F]

  implicit val canLog: CanLog

  def debugEitherT[A, B](
    efab: EitherT[F, A, B]
  )(
    a2String: A => String
  , b2String: B => String
  ): EitherT[F, A, B] =
    EitherT(
      LoggerEither[F].debugEither(efab.value)(a2String, b2String)
    )

  def infoEitherT[A, B](
    efab: EitherT[F, A, B]
  )(
    a2String: A => String
  , b2String: B => String
  ): EitherT[F, A, B] =
    EitherT(
      LoggerEither[F].infoEither(efab.value)(a2String, b2String)
    )

  def warnEitherT[A, B](
    efab: EitherT[F, A, B]
  )(
    a2String: A => String
  , b2String: B => String
  ): EitherT[F, A, B] =
    EitherT(
      LoggerEither[F].warnEither(efab.value)(a2String, b2String)
    )

  def errorEitherT[A, B](
    efab: EitherT[F, A, B]
  )(
    a2String: A => String
  , b2String: B => String
  ): EitherT[F, A, B] =
    EitherT(
      LoggerEither[F].errorEither(efab.value)(a2String, b2String)
    )
}

object LoggerEitherT {
  def apply[F[_] : LoggerEitherT]: LoggerEitherT[F] = implicitly[LoggerEitherT[F]]

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
  implicit def loggerEitherT[F[_]](
    implicit EF: EffectConstructor[F], MF: Monad[F], logger: CanLog
  ): LoggerEitherT[F] = new LoggerEitherTF[F]

  final class LoggerEitherTF[F[_]](
      @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
      implicit override val EF0: EffectConstructor[F]
    , override val MF0: Monad[F]
    , override val canLog: CanLog
  ) extends LoggerEitherT[F]

}
