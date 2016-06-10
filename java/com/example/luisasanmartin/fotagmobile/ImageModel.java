package com.example.luisasanmartin.fotagmobile;

// Note!  Nothing from the view package is imported here.
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.ListIterator;

public class ImageModel extends Object {
	/* A list of the model's views. */
	private ArrayList<IView> views = new ArrayList<IView>();

	/* The object's data */
	private int rating; // value from 0 to 5
	private ImageCollectionModel parent;
	private String filepath;
	private Bitmap image;

	public ImageModel(ImageCollectionModel p, Bitmap b, int r) {
		this(p, b);
		rating = r;
	}

	public ImageModel(ImageCollectionModel p, Bitmap b) {
		rating = 0;
		parent = p;
        image = b;
	}

	public String getFilePath() {
		return filepath;
	}

	public int getRating() {
		return rating;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setRating(int r) {
		rating = r;
		this.updateAllViews();
		parent.ratingChanged();
	}
	/** Add a new view of this triangle. */
	public void addView(IView view) {
		this.views.add(view);
		view.updateView();
	}

	/** Remove a view from this triangle. */
	public void removeView(IView view) {
		this.views.remove(view);
	}

	/** Update all the views that are viewing this triangle. */
	private void updateAllViews() {
		for (IView view : this.views) {
			view.updateView();
		}
	}
}