/*
 * Copyright 2017 Victor Albertos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.reactivecache2;

import io.rx_cache2.internal.ProcessorProviders;
import java.util.List;

public final class ProviderList<T> extends Provider<List<T>> {
  ProviderList(ProviderBuilder<List<T>> builder) {
    super(builder);
  }

  public ActionsList<T> entries() {
    return ActionsList.with(elements -> elements.compose(replace()),
        read().onErrorResumeNext(exceptionAdapter::emptyListIfRxCacheException));
  }

  public static class ProviderBuilderList<T> extends ProviderBuilder<List<T>> {
    ProviderBuilderList(ProcessorProviders processorProviders) {
      super(processorProviders);
    }

    @Override public <R extends Provider<List<T>>> R withKey(Object key) {
      this.key = key.toString();
      return (R) new ProviderList<>(this);
    }
  }
}
