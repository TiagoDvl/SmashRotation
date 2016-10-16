package br.com.tick.rotation.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import br.com.tick.rotation.R;
import br.com.tick.rotation.domain.Contest;
import br.com.tick.rotation.listener.ISmashRotation;
import br.com.tick.rotation.utils.DisplayUtil;

public class ResultsFragment extends Fragment implements OnClickListener {

    private transient ISmashRotation listener;
    private transient Contest contest;

    private transient TextView numberOfGames;
    private transient TextView bestPlayer;
    private transient TextView worstPlayer;
    private transient TextView location;

    private transient TextView numberOfGamesInfo;
    private transient TextView bestPlayerInfo;
    private transient TextView worstPlayerInfo;
    private transient TextView locationInfo;

    private transient Button shareResult;
    private transient Button restartRotation;
    private transient TextView rotationTopBar;
    private transient TextView resultsText;

    private transient RelativeLayout holderMasterResults;

    public ResultsFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.listener = (ISmashRotation) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        DisplayUtil.setLayoutParams((ViewGroup) rootView);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oswald-Regular.ttf");
        Typeface typefaceLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oswald-Light.ttf");

        numberOfGames = (TextView) rootView.findViewById(R.id.number_of_games_text);
        bestPlayer = (TextView) rootView.findViewById(R.id.best_player_text);
        worstPlayer = (TextView) rootView.findViewById(R.id.worst_player_text);
        location = (TextView) rootView.findViewById(R.id.location_text);

        numberOfGamesInfo = (TextView) rootView.findViewById(R.id.number_of_games);
        bestPlayerInfo = (TextView) rootView.findViewById(R.id.best_player);
        worstPlayerInfo = (TextView) rootView.findViewById(R.id.worst_player);
        locationInfo = (TextView) rootView.findViewById(R.id.location);

        shareResult = (Button) rootView.findViewById(R.id.share_results);
        restartRotation = (Button) rootView.findViewById(R.id.restart_rotation);

        rotationTopBar = (TextView) rootView.findViewById(R.id.top_bar_app_name);
        resultsText = (TextView) rootView.findViewById(R.id.results_text);

        holderMasterResults = (RelativeLayout) rootView.findViewById(R.id.holder_master_results);

        numberOfGames.setTypeface(typefaceLight);
        bestPlayer.setTypeface(typefaceLight);
        worstPlayer.setTypeface(typefaceLight);
        location.setTypeface(typefaceLight);

        numberOfGamesInfo.setTypeface(typeface);
        bestPlayerInfo.setTypeface(typeface);
        worstPlayerInfo.setTypeface(typeface);
        locationInfo.setTypeface(typeface);

        shareResult.setTypeface(typeface);
        restartRotation.setTypeface(typeface);

        rotationTopBar.setTypeface(typeface);
        resultsText.setTypeface(typeface);

        numberOfGamesInfo.setText(contest.getNumberOfGames() + "");
        bestPlayerInfo.setText(contest.getBestPlayer().getName());
        worstPlayerInfo.setText(contest.getWorstPlayer().getName());
        locationInfo.setText(contest.getLocation());

        shareResult.setOnClickListener(this);
        restartRotation.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_results:
                Bitmap image = getBitmapFromView(holderMasterResults);
                SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
                SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
                ShareDialog dialog = new ShareDialog(getActivity());
                if (dialog.canShow(SharePhotoContent.class)) {
                    dialog.show(content);
                }
                break;
            case R.id.restart_rotation:

                int backStackEntryCount = getActivity().getFragmentManager().getBackStackEntryCount();

                for (int i = 0; i < backStackEntryCount - 1; i++) {
                    listener.popBackStack();
                }
                break;

            default:
                break;
        }

    }

    public void setContest(Contest contest) {
        this.contest = contest;

    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
}