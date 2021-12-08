package partyround.unit.types;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UnitResponse<T> {
  public static class UnitError {
    final String message;
    final String code;
    final String detail;

    UnitError(String message, String code, String detail) {
      this.message = message;
      this.code = code;
      this.detail = detail;
    }

    public static UnitError ofMessageAndCodeAndDetail(String message, String code, String detail) {
      return new UnitError(message, code, detail);
    }

    public String getMessage() {
      return message;
    }

    public String getCode() {
      return code;
    }

    public String getDetail() {
      return detail;
    }

    public String toString() {
      return "code: " + code + ", message: " + message + ", detail: " + detail;
    }
  }

  final Optional<T> data;
  final List<UnitError> errors;

  UnitResponse(Optional<T> data, List<UnitError> errors) {
    if (data.isPresent() && !errors.isEmpty()) {
      throw new IllegalArgumentException("Cannot have both data and error");
    } else if (!data.isPresent() && errors.isEmpty()) {
      throw new IllegalArgumentException("Must have either data or error");
    }
    this.data = data;
    this.errors = errors;
  }

  public static <T> UnitResponse<T> ofErrors(List<UnitError> errors) {
    return new UnitResponse<>(Optional.empty(), errors);
  }

  public static <T> UnitResponse<T> ofResponse(T response) {
    return new UnitResponse<>(Optional.of(response), ImmutableList.of());
  }

  public boolean isError() {
    return !errors.isEmpty();
  }

  public T getData() {
    return data.get();
  }

  public Optional<T> getOptionalData() {
    return data;
  }

  public ImmutableList<UnitError> getErrors() {
    return ImmutableList.copyOf(errors);
  }

  public <R> UnitResponse<R> map(Function<T, R> fn) {
    if (isError()) {
      return UnitResponse.<R>ofErrors(errors);
    }
    return UnitResponse.ofResponse(fn.apply(data.get()));
  }
}
