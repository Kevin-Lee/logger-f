package loggerf.cats

import cats._
import cats.data.{EitherT, OptionT}
import cats.implicits._

import effectie.cats.Effectful._
import effectie.cats.EffectConstructor

import loggerf.LeveledMessage
import loggerf.LeveledMessage.{MaybeIgnorable, NotIgnorable}
import loggerf.logger.CanLog
import loggerf.syntax._

/**
 * @author Kevin Lee
 * @since 2020-04-10
 */
trait Log[F[_]] {

  implicit val EF0: EffectConstructor[F]
  implicit val MF0: Monad[F]

  val canLog: CanLog

  def log[A](fa: F[A])(toLeveledMessage: A => LeveledMessage with NotIgnorable): F[A] =
    MF0.flatMap(fa) { a =>
      toLeveledMessage(a) match {
        case LeveledMessage.LogMessage(message, level) =>
          effectOf(getLogger(canLog, level)(message)) *> effectOf(a)
      }
    }

  def logPure[A](fa: F[A])(toLeveledMessage: A => LeveledMessage with NotIgnorable): F[A] =
    MF0.flatMap(fa) { a =>
      toLeveledMessage(a) match {
        case LeveledMessage.LogMessage(message, level) =>
          effectOf(getLogger(canLog, level)(message)) *> pureOf(a)
      }
    }

  def log[A](
      foa: F[Option[A]]
    )(
      ifEmpty: => LeveledMessage with MaybeIgnorable
    , toLeveledMessage: A => LeveledMessage with MaybeIgnorable
    ): F[Option[A]] =
    MF0.flatMap(foa) {
      case None =>
        ifEmpty match {
          case LeveledMessage.Ignore =>
            pureOf(none[A])

          case LeveledMessage.LogMessage(message, level) =>
            effectOf(getLogger(canLog, level)(message)) *> pureOf(none[A])
        }
      case Some(a) =>
        toLeveledMessage(a) match {
          case LeveledMessage.LogMessage(message, level) =>
            effectOf(getLogger(canLog, level)(message)) *> effectOf(a.some)

          case LeveledMessage.Ignore =>
            effectOf(a.some)
        }
    }

  def logPure[A](
      foa: F[Option[A]]
    )(
      ifEmpty: => LeveledMessage with MaybeIgnorable
    , toLeveledMessage: A => LeveledMessage with MaybeIgnorable
    ): F[Option[A]] =
    MF0.flatMap(foa) {
      case None =>
        ifEmpty match {
          case LeveledMessage.Ignore =>
            pureOf(none[A])

          case LeveledMessage.LogMessage(message, level) =>
            effectOf(getLogger(canLog, level)(message)) *> pureOf(none[A])
        }
      case Some(a) =>
        toLeveledMessage(a) match {
          case LeveledMessage.LogMessage(message, level) =>
            effectOf(getLogger(canLog, level)(message)) *> pureOf(a.some)

          case LeveledMessage.Ignore =>
            pureOf(a.some)
        }
    }

  def log[A, B](
      feab: F[Either[A, B]]
    )(
      leftToMessage: A => LeveledMessage with MaybeIgnorable
    , rightToMessage: B => LeveledMessage with MaybeIgnorable
    ): F[Either[A, B]] =
    MF0.flatMap(feab) {
    case Left(l) =>
      leftToMessage(l) match {
        case LeveledMessage.LogMessage(message, level) =>
          effectOf(getLogger(canLog, level)(message)) *> effectOf(l.asLeft[B])

        case LeveledMessage.Ignore =>
          effectOf(l.asLeft[B])
      }
    case Right(r) =>
      rightToMessage(r) match {
        case LeveledMessage.LogMessage(message, level) =>
          effectOf(getLogger(canLog, level)(message)) *> effectOf(r.asRight[A])

        case LeveledMessage.Ignore =>
          effectOf(r.asRight[A])
      }
  }

  def logPure[A, B](
      feab: F[Either[A, B]]
    )(
      leftToMessage: A => LeveledMessage with MaybeIgnorable
    , rightToMessage: B => LeveledMessage with MaybeIgnorable
    ): F[Either[A, B]] =
    MF0.flatMap(feab) {
    case Left(l) =>
      leftToMessage(l) match {
        case LeveledMessage.LogMessage(message, level) =>
          effectOf(getLogger(canLog, level)(message)) *> pureOf(l.asLeft[B])

        case LeveledMessage.Ignore =>
          pureOf(l.asLeft[B])
      }
    case Right(r) =>
      rightToMessage(r) match {
        case LeveledMessage.LogMessage(message, level) =>
          effectOf(getLogger(canLog, level)(message)) *> pureOf(r.asRight[A])

        case LeveledMessage.Ignore =>
          pureOf(r.asRight[A])
      }
  }

  def log[A](
      otfa: OptionT[F, A]
    )(
      ifEmpty: => LeveledMessage with MaybeIgnorable
    , toLeveledMessage: A => LeveledMessage with MaybeIgnorable
    ): OptionT[F, A] =
    OptionT(log(otfa.value)(ifEmpty, toLeveledMessage))

  def logPure[A](
      otfa: OptionT[F, A]
    )(
      ifEmpty: => LeveledMessage with MaybeIgnorable
    , toLeveledMessage: A => LeveledMessage with MaybeIgnorable
    ): OptionT[F, A] =
    OptionT(logPure(otfa.value)(ifEmpty, toLeveledMessage))


  def log[A, B](
      etfab: EitherT[F, A, B]
    )(
      leftToMessage: A => LeveledMessage with MaybeIgnorable
    , rightToMessage: B => LeveledMessage with MaybeIgnorable
    ): EitherT[F, A, B] =
    EitherT(log(etfab.value)(leftToMessage, rightToMessage))

  def logPure[A, B](
      etfab: EitherT[F, A, B]
    )(
      leftToMessage: A => LeveledMessage with MaybeIgnorable
    , rightToMessage: B => LeveledMessage with MaybeIgnorable
    ): EitherT[F, A, B] =
    EitherT(logPure(etfab.value)(leftToMessage, rightToMessage))

}

object Log {

  def apply[F[_] : Log]: Log[F] = implicitly[Log[F]]

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
  implicit def logF[F[_]](
    implicit EF: EffectConstructor[F], EM: Monad[F], logger: CanLog
  ): Log[F] =
    new LogF[F](EF, EM, logger)

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
  final class LogF[F[_]](
    override val EF0: EffectConstructor[F]
  , override val MF0: Monad[F]
  , override val canLog: CanLog
  ) extends Log[F]

}