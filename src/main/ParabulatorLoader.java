package main;

import display.parabulators.Parabulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParabulatorLoader
{
	public static List<Parabulator> loadParabulators()
	{
		List<Parabulator> parabulators = new ArrayList<>();

		try
		{
			ClassLoader cl = new URLClassLoader(new URL[]{ParabulatorLoader.class.getResource("/")});

			for(String current : loadConfig("/display/parabulators/expansions/config"))
			{
				Class<?> clazz;
				try
				{
					clazz = cl.loadClass("display.parabulators.expansions." + current);
				} catch(NullPointerException e)
				{
					System.err.printf("Couldn't load %s\n", current);
					continue;
				}

				parabulators.add((Parabulator) clazz.getConstructor((Class<?>) null).newInstance((Object) null));
				System.out.println("Loaded class " + current);
			}

		} catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | IOException e)
		{
			System.err.println("Couldn't load parabulators");
		}

		Collections.sort(parabulators);

		return parabulators;
	}

	private static List<String> loadConfig(String path) throws IOException
	{
		List<String> config = new ArrayList<>();

		InputStream in = ParabulatorLoader.class.getResourceAsStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String file;

		while((file = reader.readLine()) != null)
		{
			config.add(file);
		}

		return config;
	}
}
