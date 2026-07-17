# Repository instructions

## Project overview

This repository contains personal Morphe patches for Android applications.

## Development workflow

- Do all development work on the `dev` branch.
- Use the [Morphe patcher documentation](https://github.com/MorpheApp/morphe-patcher/blob/main/docs/1_patcher_intro.md) for examples of patches and fingerprints.
- Use the [Morphe documentation](https://github.com/MorpheApp/morphe-documentation) for general patch development guidance.
- Build patches with `./gradlew buildAndroid`.
- The build produces an MPP bundle at `patches/build/libs/patches-*.mpp`.
- Apply the generated bundle locally with Morphe CLI in the same way as any other patch bundle.

## Generated files

- Do not manually edit `patches-list.json`, `patches-bundle.json`, or `CHANGELOG.md`.
- Do not manually edit the generated patch-list block in `README.md` between `PATCHES_START` and `PATCHES_END`.
- The release workflow updates these files and the README patch list.
- Preserve the `EXPANDED` suffix on `PATCHES_START` so the generated patch list remains expanded.
- Remove the patch-list markers only when the user explicitly requests a manually maintained list.

## Commits

- Use [semantic commit messages](https://kapeli.com/cheat_sheets/Semantic_Commits.docset/Contents/Resources/Documents/index).
- Use `feat:` for new user-facing functionality.
- Use `fix:` for user-facing fixes.
- Use `chore:` for changes that should not appear in the user-facing changelog.
- `feat:` and `fix:` commits trigger new prereleases; `chore:` commits do not create a release.

## Releases

- Development releases are published from `dev`. Users must enable `pre-release` for this source in Morphe Manager to receive them.
- For a stable release, merge `dev` into `main` with a merge commit. Do not squash the merge.
- Always release through `.github/workflows/release.yml` and semantic-release.
- Do not manually create or upload releases because the workflow must update multiple generated files.
- Do not force-push semantic-release commits; doing so can break the release history.
- Rewriting release history, deleting a release or tag, and force-pushing require explicit user authorization. If the user asks to redo the latest release, drop the relevant semantic-release commit from `dev` or `main`, delete the associated release and tag, make the required changes, and then force-push the affected branch so `release.yml` can create the replacement release.

## Repository setup notes

- Keep links in `.github/ISSUE_TEMPLATE/` up to date.
- An optional `patches-bundle.png` can be added to show a custom bundle icon in Morphe Manager instead of the GitHub profile avatar.
