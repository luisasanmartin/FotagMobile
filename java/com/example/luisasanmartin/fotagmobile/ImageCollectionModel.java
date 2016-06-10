package com.example.luisasanmartin.fotagmobile;

// Note!  Nothing from the view package is imported here.
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.ListIterator;
import java.io.*;

public class ImageCollectionModel extends Object {
	/* A list of the model's views. */
	private ArrayList<IView> views = new ArrayList<IView>();

	private ArrayList<ImageModel> allImages = new ArrayList<ImageModel>();
	private ArrayList<ImageModel> imagesToDisplay = new ArrayList<ImageModel>();

	private int minRating = 0;
	private boolean isGridView = true;
	
	// Override the default construtor, making it private.
	public ImageCollectionModel() {
		//loadFromFile();
		setMinRating(0);
        System.out.println("here");
		this.updateAllViews();
	}


	public ArrayList<ImageModel> getImages() {
		return imagesToDisplay;
	}

	public void addImage(Bitmap b) {
		ImageModel newImage = new ImageModel(this, b);
		allImages.add(newImage);
		imagesToDisplay.add(newImage);
        System.out.println("imagestodisplay size: " + imagesToDisplay.size());
		this.updateAllViews();
	}

	public Bitmap getImage(int position) {
		return imagesToDisplay.get(position).getImage();
	}

    public ImageModel getImageModel(int position) {
        return imagesToDisplay.get(position);
    }

    public int getImageCount() {
        return imagesToDisplay.size();
    }

	public void setMinRating(int r) {
		minRating = r;
		imagesToDisplay = new ArrayList<ImageModel>();
		for (ImageModel i: allImages) {
			if (i.getRating() >= minRating) {
				imagesToDisplay.add(i);
			}
		}
		this.updateAllViews();
	}

    public int getMinRating() {
        return minRating;
    }

	public void ratingChanged() {
		if (minRating != 0) {
			imagesToDisplay = new ArrayList<ImageModel>();
			for (ImageModel i: allImages) {
				if (i.getRating() >= minRating) {
					imagesToDisplay.add(i);
				}
			}
			this.updateAllViews();
		}
	}

	public void clear() {
		imagesToDisplay = new ArrayList<ImageModel>();
		allImages = new ArrayList<ImageModel>();
		this.updateAllViews();

	}


	/** Add a new view of this triangle. */
	public void addView(IView view) {
        System.out.println("before adding: " + this.views.size());
		this.views.add(view);
        System.out.println("after adding: " + this.views.size());
		view.updateView();
	}

	/** Remove a view from this triangle. */
	public void removeView(IView view) {
		this.views.remove(view);
	}

	/** Update all the views that are viewing this triangle. */
	private void updateAllViews() {
        System.out.println("updating ");
		for (IView view : this.views) {
            System.out.println("here2");
			view.updateView();
		}
	}
}