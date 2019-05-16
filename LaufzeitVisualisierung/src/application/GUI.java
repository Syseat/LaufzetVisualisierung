package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

public class GUI extends Group
{
	Scene scene;

	Line xLine, yLine;

	TextField xScale;

	Button sort;

	Group LineGroup;

	int offset = 30;
	int scale = 1000;

	public GUI(Scene s)
	{
		scene = s;

		xLine = new Line();
		xLine.setStartX(offset);
		xLine.setStartY(scene.getHeight() - offset);
		xLine.setEndX(scene.getWidth());
		xLine.setEndY(scene.getHeight() - offset);
		add(xLine);

		yLine = new Line();
		yLine.setStartX(offset);
		yLine.setStartY(0);
		yLine.setEndX(offset);
		yLine.setEndY(scene.getHeight() - offset);
		add(yLine);

		xScale = new TextField("Number of Data");
		xScale.setTranslateX(scene.getWidth() - 4 * offset);
		xScale.setTranslateY(offset);
		xScale.setPrefWidth(100);
		xScale.setText("5");
		add(xScale);

		sort = new Button("sort");
		sort.setTranslateX(xScale.getTranslateX() - 2 * offset);
		sort.setTranslateY(offset);

		sort.setOnMouseClicked((MouseEvent e) ->
		{
			LineGroup.getChildren().clear();
			sort();
		});

		add(sort);

		LineGroup = new Group();
		add(LineGroup);

	}

	private void sort()
	{

		ArrayList<int[]> data = new ArrayList<int[]>();

		for (int i = 0; i < Integer.parseInt(xScale.getText()); i++)
		{
			int[] arr = new int[i];
			for (int k = 0; k < i; k++)
			{
				arr[k] = k;
			}
			arr = arrayMix(arr);
			data.add(arr);
		}

		BubbleSort(data);
		insertionSort(data);

	}

	private void BubbleSort(ArrayList<int[]> data)
	{
		long[] times = new long[data.size()];

		for (int k = 0; k < data.size(); k++)
		{
			int[] arr = data.get(k);
			long time = System.nanoTime();

			int n = arr.length;
			for (int i = 0; i < n - 1; i++)
			{
				for (int j = 0; j < n - 1; j++)
				{
					if (arr[j] > arr[i + 1])
					{
						int temp = arr[j];
						arr[j] = arr[j + 1];
						arr[j + 1] = temp;
					}
				}
			}

			time = System.nanoTime() - time;
			time /= scale;
			times[k] = time;

		}
		addLine(times, Color.RED);
	}

	private void insertionSort(ArrayList<int[]> data)
	{
		long[] times = new long[data.size()];

		for (int k = 0; k < data.size(); k++)
		{
			int[] arr = data.get(k);
			long time = System.nanoTime();

			int n = arr.length;
			for (int i = 0; i < n - 1; i++)
			{
				boolean inserted = false;
				int j = i;
				while ((j >= 1) && !inserted)
				{
					if (arr[j] < arr[j - 1])
					{
						int temp = arr[j];
						arr[j] = arr[j - 1];
						arr[j - 1] = temp;
					}
					else
					{
						inserted = true;
						j--;
					}
				}
			}

			time = System.nanoTime() - time;
			time /= scale;
			System.out.println(time);
			times[k] = time;
		}
		System.out.println();

		addLine(times, Color.BLUE);

	}

	private void add(Node n)
	{
		getChildren().add(n);
	}

	public void addLine(long[] times, Color c)
	{
		double xStep = (scene.getWidth() - offset) / times.length;

		Polyline line = new Polyline();
		line.setStroke(c);

		for (int i = 0; i < times.length; i++)
		{
			System.out.println("This is i" + i);
			line.getPoints().add(Double.parseDouble(i * xStep + offset + ""));
			line.getPoints().add(Double.parseDouble(scene.getHeight() - times[i] - offset + ""));

		}
		LineGroup.getChildren().add(line);
	}

	private static int[] arrayMix(int[] zahlen)
	{
		int tmp;
		int rand;
		Random r = new Random();
		for (int i = 0; i < zahlen.length; i++)
		{
			rand = r.nextInt(zahlen.length);
			tmp = zahlen[i];
			zahlen[i] = zahlen[rand];
			zahlen[rand] = tmp;
		}
		return zahlen;
	}

}
