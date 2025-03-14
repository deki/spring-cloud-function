/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.function.context.test;

import java.util.Locale;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.function.context.FunctionCatalog;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dave Syer
 *
 */
@FunctionalSpringBootTest
public class FunctionalTests {

	@Autowired
	private FunctionCatalog catalog;

	@Test
	public void words() throws Exception {
		Function<Flux<String>, Flux<String>> function = this.catalog
				.lookup(Function.class, "function");
		assertThat(function.apply(Flux.just("foo")).blockFirst()).isEqualTo("FOO");
	}

	@SpringBootConfiguration
	protected static class TestConfiguration implements Function<String, String> {

		@Override
		public String apply(String value) {
			return value.toUpperCase(Locale.ROOT);
		}

	}

}
